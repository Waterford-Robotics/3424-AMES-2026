package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignCommand extends Command {
  private PIDController xController, yController, rotController;
  private double targetX, targetY, targetRot;
  private Timer dontSeeTagTimer, stopTimer;
  private CommandSwerveDrivetrain drivebase;
  private double tagID = -1;

  private final SwerveRequest.RobotCentric driveRequest = new SwerveRequest.RobotCentric();

  public AlignCommand(boolean isRightScore, CommandSwerveDrivetrain drivebase) {
    this(
        Constants.X_SETPOINT_ALIGNMENT,
        isRightScore ? Constants.Y_SETPOINT_ALIGNMENT : -Constants.Y_SETPOINT_ALIGNMENT,
        Constants.ROT_SETPOINT_ALIGNMENT,
        drivebase
    );
  }

  public AlignCommand(double targetX, double targetY, double targetRot, CommandSwerveDrivetrain drivebase) {
    xController = new PIDController(Constants.X_ALIGNMENT_P, 0.0, 0);  // Vertical movement
    yController = new PIDController(Constants.Y_ALIGNMENT_P, 0.0, 0);  // Horitontal movement
    rotController = new PIDController(Constants.ROT_ALIGNMENT_P, 0, 0);  // Rotation
    this.targetX = targetX;
    this.targetY = targetY;
    this.targetRot = targetRot;
    this.drivebase = drivebase;
    addRequirements(drivebase);
  }

  @Override
  public void initialize() {
    this.stopTimer = new Timer();
    this.stopTimer.start();
    this.dontSeeTagTimer = new Timer();
    this.dontSeeTagTimer.start();

    rotController.setSetpoint(targetRot);
    rotController.setTolerance(Constants.ROT_TOLERANCE_ALIGNMENT);

    xController.setSetpoint(targetX);
    xController.setTolerance(Constants.X_TOLERANCE_ALIGNMENT);

    yController.setSetpoint(targetY);
    yController.setTolerance(Constants.Y_TOLERANCE_ALIGNMENT);

    tagID = LimelightHelpers.getFiducialID("");
  }

  @Override
  public void execute() {
    if (LimelightHelpers.getTV("") && LimelightHelpers.getFiducialID("") == tagID) {
      this.dontSeeTagTimer.reset();

      double[] postions = LimelightHelpers.getBotPose_TargetSpace("");
      SmartDashboard.putNumber("x", postions[2]);

      double xSpeed = xController.calculate(postions[2]);
      SmartDashboard.putNumber("xspee", xSpeed);
      double ySpeed = -yController.calculate(postions[0]);
      double rotValue = -rotController.calculate(postions[4]);

      drivebase.setControl(
          driveRequest
              .withVelocityX(xSpeed)
              .withVelocityY(ySpeed)
              .withRotationalRate(rotValue)
      );

      if (!rotController.atSetpoint() ||
          !yController.atSetpoint() ||
          !xController.atSetpoint()) {
        stopTimer.reset();
      }
    } else {
      drivebase.setControl(
          driveRequest
              .withVelocityX(0)
              .withVelocityY(0)
              .withRotationalRate(0)
      );
    }

    SmartDashboard.putNumber("poseValidTimer", stopTimer.get());
  }

  @Override
  public void end(boolean interrupted) {
    drivebase.setControl(
        driveRequest
            .withVelocityX(0)
            .withVelocityY(0)
            .withRotationalRate(0)
    );
  }

  @Override
  public boolean isFinished() {
    // Requires the robot to stay in the correct position for 0.3 seconds, as long as it gets a tag in the camera
    return this.dontSeeTagTimer.hasElapsed(Constants.DONT_SEE_TAG_WAIT_TIME) ||
        stopTimer.hasElapsed(Constants.POSE_VALIDATION_TIME);
  }
}
