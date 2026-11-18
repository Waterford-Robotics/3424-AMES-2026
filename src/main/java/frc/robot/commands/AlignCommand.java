package frc.robot.commands;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.VisionConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Limelight.LimelightHelpers;

// Positions Robot at the Nearest Valid Target
public class AlignCommand extends Command {

  // CTRE Swerve Drivetrain & Robot-Centric Request
  private final CommandSwerveDrivetrain m_CommandSwerveDrivetrain;
  private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();

  // Max speeds pulled from CTRE TunerConstants (replaces YAGSL SwerveConstants)
  private final double maxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
  private final double maxAngularSpeed = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

  // Timer for cancellation with failed robot adjustment
  Timer timer = new Timer();

  // PID Controllers
  PIDController m_aimController = new PIDController(VisionConstants.kP_aim, VisionConstants.kI_aim, VisionConstants.kD_aim);
  PIDController m_rangeController = new PIDController(VisionConstants.kP_range, VisionConstants.kI_range, VisionConstants.kD_range);
  PIDController m_strafeController = new PIDController(VisionConstants.kP_strafe, VisionConstants.kI_strafe, VisionConstants.kD_strafe);

  // Bot Pose Target Space Relative [TX, TY, TZ, Pitch, Yaw, Roll]
  private double[] botPoseTargetSpace = new double[6];

  // All the Valid IDs available for positioning
  int[] validIDs = {6, 7, 8, 9, 10, 11, 17, 18, 19, 20, 21, 22};

  // Boolean for checking "Tag In View"
  private boolean tiv;

  // Target Setpoints
  private double m_rangeTarget;
  private double m_strafeTarget;
  private double m_aimTarget;

  public AlignCommand(CommandSwerveDrivetrain driveSubsystem) {
    m_CommandSwerveDrivetrain = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  @Override
  public void initialize() {
    VisionConstants.k_positioned = false;

    // Filter out undesired AprilTag IDs
    LimelightHelpers.SetFiducialIDFiltersOverride(VisionConstants.k_limelightName, validIDs);

    // Update BotPoseTargetSpace
    botPoseTargetSpace = NetworkTableInstance.getDefault()
        .getTable(VisionConstants.k_limelightName)
        .getEntry("botpose_targetspace")
        .getDoubleArray(new double[6]);

    // Check for TIV
    tiv = (LimelightHelpers.getTV(VisionConstants.k_limelightName)
        && botPoseTargetSpace[2] > VisionConstants.k_tzValidRange
        && Math.abs(botPoseTargetSpace[4]) < VisionConstants.k_yawValidRange);

    // Set Constants
    if (VisionConstants.k_isRightReef) {
      m_strafeTarget = VisionConstants.k_strafeReefRightTarget;
      m_rangeTarget = VisionConstants.k_rangeReefRightTarget;
      m_aimTarget = VisionConstants.k_aimReefRightTarget;
    } else {
      m_strafeTarget = VisionConstants.k_strafeReefLeftTarget;
      m_rangeTarget = VisionConstants.k_rangeReefLeftTarget;
      m_aimTarget = VisionConstants.k_aimReefLeftTarget;
    }

    timer.restart();
  }

  @Override
  public void execute() {
    VisionConstants.k_positioning = true;

    // Update the pose from NetworkTables (Limelight Readings)
    botPoseTargetSpace = NetworkTableInstance.getDefault()
        .getTable(VisionConstants.k_limelightName)
        .getEntry("botpose_targetspace")
        .getDoubleArray(new double[6]);

    // Drive using CTRE RobotCentric SwerveRequest
    if (tiv) {
      tiv = LimelightHelpers.getTV(VisionConstants.k_limelightName)
          && botPoseTargetSpace[2] > VisionConstants.k_tzValidRange;

      m_CommandSwerveDrivetrain.setControl(
          drive.withVelocityX(limelight_range_PID())
               .withVelocityY(limelight_strafe_PID())
               .withRotationalRate(limelight_aim_PID()));
    }
  }

  @Override
  public void end(boolean interrupted) {
    VisionConstants.k_positioning = false;

    // Stop the CTRE swerve drivetrain when the command finishes or interrupts
    m_CommandSwerveDrivetrain.setControl(
        drive.withVelocityX(0)
             .withVelocityY(0)
             .withRotationalRate(0));

    if (Math.abs(botPoseTargetSpace[0] - m_strafeTarget) < VisionConstants.k_strafeThreshold
        && Math.abs(botPoseTargetSpace[2] - m_rangeTarget) < VisionConstants.k_rangeThreshold
        && Math.abs(botPoseTargetSpace[4] - m_aimTarget) < VisionConstants.k_aimThreshold) {
      VisionConstants.k_positioned = true;
    }
  }

  @Override
  public boolean isFinished() {
    return (Math.abs(botPoseTargetSpace[0] - m_strafeTarget) < VisionConstants.k_strafeThreshold
        && Math.abs(botPoseTargetSpace[2] - m_rangeTarget) < VisionConstants.k_rangeThreshold
        && Math.abs(botPoseTargetSpace[4] - m_aimTarget) < VisionConstants.k_aimThreshold)
        || !tiv
        || timer.get() > 2;
  }

  // Strafe velocity in m/s (+Y = left in CTRE RobotCentric)
  private double limelight_strafe_PID() {
    double targetingStrafeSpeed = m_strafeController.calculate(botPoseTargetSpace[0] - m_strafeTarget);
    targetingStrafeSpeed *= -1.0 * maxSpeed;
    return targetingStrafeSpeed;
  }

  // Forward velocity in m/s (+X = forward in CTRE RobotCentric)
  private double limelight_range_PID() {
    double targetingForwardSpeed = m_rangeController.calculate(botPoseTargetSpace[2] - m_rangeTarget);
    targetingForwardSpeed *= 1.0 * maxSpeed;
    return targetingForwardSpeed;
  }

  // Angular velocity in rad/s (+CCW in CTRE RobotCentric)
  private double limelight_aim_PID() {
    m_aimController.enableContinuousInput(-30, 30);
    double targetingAngularVelocity = m_aimController.calculate(botPoseTargetSpace[4] - m_aimTarget);
    targetingAngularVelocity *= -0.1 * maxAngularSpeed;
    return targetingAngularVelocity;
  }
}