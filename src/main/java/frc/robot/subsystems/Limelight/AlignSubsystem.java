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
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private static final String DRIVE_P_KEY = "Alignment/Drive P";
    private static final String DRIVE_I_KEY = "Alignment/Drive I";
    private static final String DRIVE_D_KEY = "Alignment/Drive D";
    private static final String ROTATION_P_KEY = "Alignment/Rotation P";
    private static final String ROTATION_I_KEY = "Alignment/Rotation I";
    private static final String ROTATION_D_KEY = "Alignment/Rotation D";

    public AlignSubsystem() {
        rotPID.enableContinuousInput(-Math.PI, Math.PI);
        SmartDashboard.putNumber(DRIVE_P_KEY, Constants.AlignmentConstants.pDrive);
        SmartDashboard.putNumber(DRIVE_I_KEY, Constants.AlignmentConstants.iDrive);
        SmartDashboard.putNumber(DRIVE_D_KEY, Constants.AlignmentConstants.dDrive);
        SmartDashboard.putNumber(ROTATION_P_KEY, Constants.AlignmentConstants.pRot);
        SmartDashboard.putNumber(ROTATION_I_KEY, Constants.AlignmentConstants.iRot);
        SmartDashboard.putNumber(ROTATION_D_KEY, Constants.AlignmentConstants.dRot);
    }

    private void updatePIDGainsFromDashboard() {
        double driveP = SmartDashboard.getNumber(DRIVE_P_KEY, Constants.AlignmentConstants.pDrive);
        double driveI = SmartDashboard.getNumber(DRIVE_I_KEY, Constants.AlignmentConstants.iDrive);
        double driveD = SmartDashboard.getNumber(DRIVE_D_KEY, Constants.AlignmentConstants.dDrive);
        driveXPID.setPID(driveP, driveI, driveD);
        driveYPID.setPID(driveP, driveI, driveD);

        rotPID.setPID(
                SmartDashboard.getNumber(ROTATION_P_KEY, Constants.AlignmentConstants.pRot),
                SmartDashboard.getNumber(ROTATION_I_KEY, Constants.AlignmentConstants.iRot),
                SmartDashboard.getNumber(ROTATION_D_KEY, Constants.AlignmentConstants.dRot));
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

        updatePIDGainsFromDashboard();

        boolean hasFiducial = LimelightHelpers.getTV("limelight")
                && LimelightHelpers.getFiducialID("limelight") >= 0;
        boolean hasValidFieldPose = false;
        double xSpeed = 0;
        double ySpeed = 0;
        double rotationSpeed = 0;
        double tagAngleError = 0;
        if (hasFiducial) {
            var poseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight");
            hasValidFieldPose = LimelightHelpers.validPoseEstimate(poseEstimate);
            if (hasValidFieldPose) {
                xSpeed = driveXPID.calculate(poseEstimate.pose.getX(), VisionConstants.targetX[targetIndex]);
                ySpeed = driveYPID.calculate(poseEstimate.pose.getY(), VisionConstants.targetY[targetIndex]);
            } else {
                driveXPID.reset();
                driveYPID.reset();
            }

            var targetPoseRobotSpace = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");
            var tagNormalRobotSpace = new Translation3d(0, 0, 1)
                    .rotateBy(targetPoseRobotSpace.getRotation());
            double tagNormalYaw = Math.atan2(tagNormalRobotSpace.getY(), tagNormalRobotSpace.getX());
            double parallelError = MathUtil.inputModulus(Math.PI - tagNormalYaw, -Math.PI, Math.PI);
            double targetAngleRadians = Math.toRadians(VisionConstants.targetAngle[targetIndex]);
            tagAngleError = MathUtil.inputModulus(targetAngleRadians - parallelError, -Math.PI, Math.PI);
            rotationSpeed = rotPID.calculate(parallelError, targetAngleRadians);
        } else {
            driveXPID.reset();
            driveYPID.reset();
            rotPID.reset();
        }
        double maxRotation = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
        rotationSpeed = MathUtil.clamp(rotationSpeed, -maxRotation, maxRotation);

        if (hasFiducial && hasValidFieldPose && Math.abs(tagAngleError) <= 0.05) {
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
