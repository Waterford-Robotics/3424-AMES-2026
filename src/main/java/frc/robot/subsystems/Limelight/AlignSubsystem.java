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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.RobotContainer;

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


    public void align(CommandSwerveDrivetrain swerve, int targetIndex) {
        
        double tagYaw = LimelightHelpers
        .getTargetPose3d_RobotSpace("limelight")
        .getRotation()
        .getZ();
        double robotX=-LimelightHelpers.getTargetPose3d_RobotSpace("limelight").getX();
        double robotY=-LimelightHelpers.getTargetPose3d_RobotSpace("limelight").getY();
        SmartDashboard.putNumber("robotX", robotX);
        SmartDashboard.putNumber("robotY", robotY);
        SmartDashboard.putNumber("Rotation", tagYaw);
        if(!(LimelightHelpers.getTV("limelight"))||(targetIndex==-1)||!(Math.hypot(robotY, robotX)<=3)){
            swerveStop(swerve);
            return;
        }
        
        // if(RobotContainer.tolerant(tagYaw, 0.1, 0)){
        //     swerve.setControl(drive
        //     .withVelocityX(driveXPID.calculate(robotX,Constants.VisionConstants.targetX[targetIndex]))
        //     .withVelocityY(driveYPID.calculate(robotY,Constants.VisionConstants.targetY[targetIndex]))
        //     .withRotationalRate(MathUtil.clamp(rotPID.calculate(tagYaw,0), -1, 1)));
        //     swerve.setControl(drive);
        // }else{
            
        // } 
        swerve.setControl(drive.withRotationalRate(MathUtil.clamp(rotPID.calculate(tagYaw,0), -1, 1)));
    }

    public void swerveStop(CommandSwerveDrivetrain swerve) {
        if (swerve != null) {
            swerve.setControl(drive.withVelocityX(0).withVelocityY(0).withRotationalRate(0));
        }
    }

   
}

/*
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
 */