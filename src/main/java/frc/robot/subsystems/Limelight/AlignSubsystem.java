package frc.robot.subsystems.Limelight;

import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignSubsystem extends SubsystemBase {
    private final PIDController rotPID = new PIDController(Constants.AlignmentConstants.pRot,
            Constants.AlignmentConstants.iRot, Constants.AlignmentConstants.dRot);
    private final PIDController driveXPID = new PIDController(Constants.AlignmentConstants.pDrive,
            Constants.AlignmentConstants.iDrive, Constants.AlignmentConstants.dDrive);
    private final PIDController driveYPID = new PIDController(Constants.AlignmentConstants.pDrive,
            Constants.AlignmentConstants.iDrive, Constants.AlignmentConstants.dDrive);
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    private double MaxSpeed = 2;

    public AlignSubsystem() {
        rotPID.enableContinuousInput(-Math.PI, Math.PI);
    }

    /** targetIndex is the zero-based index derived from AlignCommand.getTargetAlign(). */
    public void align(CommandSwerveDrivetrain swerve, int targetIndex) {
        // The target arrays currently contain only placeholder values. Never index past
        // configured data or drive toward an unrelated target when a slot is unconfigured.
        if (swerve == null || targetIndex < 0 || targetIndex >= VisionConstants.targetX.length
                || targetIndex >= VisionConstants.targetY.length
                || targetIndex >= VisionConstants.targetAngle.length) {
            swerveStop(swerve);
            return;
        }
        

        boolean hasFiducial = LimelightHelpers.getTV("limelight")
                && LimelightHelpers.getFiducialID("limelight") >= 0;
        double xSpeed = 0;
        double ySpeed = 0;
        if (hasFiducial) {
            var botPose = VisionSubsystem.getBotPose();
            xSpeed = driveXPID.calculate(botPose.getX(), VisionConstants.targetX[targetIndex]);
            ySpeed = driveYPID.calculate(botPose.getY(), VisionConstants.targetY[targetIndex]);
        } else {
            driveXPID.reset();
            driveYPID.reset();
        }
        var tagPose = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");
        double headingError = tagPose.getRotation().getZ();
        

        double rotationSpeed = rotPID.calculate(
          headingError,
          Math.toRadians(VisionConstants.targetAngle[targetIndex]));
        double maxRotation = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
        rotationSpeed = MathUtil.clamp(rotationSpeed, -maxRotation, maxRotation);

        if (hasFiducial && Math.abs(headingError) <= 0.05) {
            Pose2d velocity = xySpeed(xSpeed, ySpeed, MaxSpeed);
            swerve.setControl(drive.withVelocityX(velocity.getX())
                    .withVelocityY(velocity.getY())
                    .withRotationalRate(rotationSpeed));
        } else {
            swerve.setControl(drive.withVelocityX(0).withVelocityY(0).withRotationalRate(rotationSpeed));
        }
    }

    public void swerveStop(CommandSwerveDrivetrain swerve) {
        if (swerve != null) {
            swerve.setControl(drive.withVelocityX(0).withVelocityY(0).withRotationalRate(0));
        }
    }

    public static Pose2d xySpeed(double x, double y, double maxSpeed) {
        double currentSpeed = Math.hypot(x, y);
        if (currentSpeed > maxSpeed && currentSpeed > 0) {
            double scale = maxSpeed / currentSpeed;
            x *= scale;
            y *= scale;
        }
        return new Pose2d(x, y, new Rotation2d());
    }
}