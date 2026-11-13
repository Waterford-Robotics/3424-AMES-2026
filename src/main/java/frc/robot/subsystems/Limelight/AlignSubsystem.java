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
        double Yaw=VisionSubsystem.getBotPose().getRotation().getZ();

        
        swerve.setControl(drive.withRotationalRate(-MathUtil.clamp(1, -1, rotPID.calculate(VisionSubsystem.getBotPose().getRotation().getZ(),0))));
        
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
