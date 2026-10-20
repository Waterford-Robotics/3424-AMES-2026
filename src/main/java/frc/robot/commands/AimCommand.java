package frc.robot.commands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight.ApriltagHelpers;
import frc.robot.subsystems.Limelight.LimelightHelpers;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveRequest;
// Aims!

public class AimCommand extends Command {
  // Makes instances of Stuff
  CommandSwerveDrivetrain swerveSubsystem;
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric();

  // Timer so it doesn't take 10 years and eventually "times out" if fails too hard
  Timer timer = new Timer();
    
  // Valid IDs available for positioning
  // On Red Hub Offset: 3, 8, 9, 11 // On Red Hub Centered: 2, 4, 5, 10
  // On Blue Hub: 19, 24, 25, 27 // On Blue Hub Centered: 18, 20, 21, 26
  int[] validIDs = {2, 4, 5, 10, 18, 20, 21, 26};

  // Checks if the a tag is seen
  boolean sees_tag;

  double target_distance_to_hub;
  // Constructor
  public AimCommand(CommandSwerveDrivetrain m_swerveSubsystem) {
    // swerveSubsystem is an instance of SwerveSubsystem class, aka an object
    swerveSubsystem = m_swerveSubsystem;
    addRequirements(swerveSubsystem);
  }

  // What we do to set up the command
  @Override
  public void initialize() {
    // Adds condition that filters out undesired IDs. Check for the valid colors.
    LimelightHelpers.SetFiducialIDFiltersOverride(Constants.VisionConstants.LimelightName, validIDs);
    // Timer Reset
    timer.start();
    timer.reset();
  }


  // Method that returns a double for how fast the robot needs to turn, farther angle from the tag is a faster turn
  private double limelight_rotation() {
    //double targetingAngularVelocity = pidController.calculate(LimelightHelpers.getTX(Constants.VisionConstants.LimelightName), 0);
    // use k_turnRate instead as a proportion? What is k_turnRate (part of Swerve Subsystem)?
    // gives this value to the swerve drive to start driving!
    // turnScale is a constant instead of k_turnRate that gives a proportional scaling to the horizontal angle offset
    
    // positive angle = april tag to the right, turn clockwise, negate
    // negative angle = april tag to the left, turn counterclockwise, positive
    double TX = Math.toRadians(LimelightHelpers.getTX(Constants.VisionConstants.LimelightName));
    double targetingAngularVelocity = 
    // ex: target angle = 45 degrees / 0.78 radians (right), 
    //      TX = 10 degrees / 0.17 radians (right)
    //      we want to turn right, at negative (target_angle - TX), so constant is negative
    //      0.6 radians difference, 0.3 power? set constant to 0.5 initially
    ((ApriltagHelpers.getTargetAngle() - TX) * Constants.VisionConstants.turnScale);
    double rotationSpeed = MathUtil.clamp(targetingAngularVelocity, -0.5, 0.5);
    return rotationSpeed; 
  }

  // The actual control
  @Override
  public void execute() {
    // Checks for TV (if it sees tag)
    sees_tag = LimelightHelpers.getTV(Constants.VisionConstants.LimelightName);
    // If tags are in view, rotate at a speed proportional to the offset robot relative!
    // passes lambdas for DoubleSuppliers because it is called repeatedly
    if (sees_tag) 
    {
      swerveSubsystem.setControl(drive.withRotationalRate(limelight_rotation())); // Drive counterclockwise with negative X (left)
     }
    // Otherwise we tell it to quit
    else sees_tag = false;
  }
  // Add stuff we do after to reset here. the thing that happens once at the very end of the command. Either because isFinished or interrupted
  public void end(boolean interrupted) {
  }

  // Are we done yet? Finishes when threshold is reached or if no tag in view or if timer is reached
  @Override
  public boolean isFinished() {
    double TX = Math.toRadians(LimelightHelpers.getTX(Constants.VisionConstants.LimelightName));
    return (Math.abs(TX) < Constants.VisionConstants.kAimThreshold || !sees_tag || timer.get() > Constants.VisionConstants.timeoutTime);
  }


}