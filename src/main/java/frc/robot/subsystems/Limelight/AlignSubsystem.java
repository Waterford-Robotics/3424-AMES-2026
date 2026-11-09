package frc.robot.subsystems.Limelight;

import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.AlignmentConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignSubsystem extends SubsystemBase{
    private static PIDController rotPID = new PIDController(Constants.AlignmentConstants.pRot, Constants.AlignmentConstants.iRot, Constants.AlignmentConstants.dRot);
    private static PIDController drivexPID= new PIDController(Constants.AlignmentConstants.pDrive, Constants.AlignmentConstants.iDrive, Constants.AlignmentConstants.dDrive);
    private static PIDController driveyPID= new PIDController(Constants.AlignmentConstants.pDrive, Constants.AlignmentConstants.iDrive, Constants.AlignmentConstants.dDrive);
    
    static CommandSwerveDrivetrain swerveSubsystem;
     
    
    


    public static void Align(CommandSwerveDrivetrain m_swerve){
        final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
        double xError=0;//need to get the target uisng arrays and align
        double yError=0;//same as above
        yError=driveyPID.calculate(VisionSubsystem.getBotPose().getY(),yError);
        xError=drivexPID.calculate(VisionSubsystem.getBotPose().getY(),xError);
         rotPID.enableContinuousInput(-Math.PI, Math.PI);
       
       
        
        
                double rotationSpeed=rotPID.calculate(m_swerve.getGyro(),VisionConstants.targetAngle[1]);
        
        
                if(RobotContainer.tolerant(m_swerve.getGyro(), 0.05, VisionConstants.targetAngle[1]))
                {
        
                m_swerve.setControl(
                    drive.withVelocityY(xySpeed(xError, yError, TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)).getY())
                         .withVelocityX(xySpeed(xError, yError, TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)).getX())
                         .withRotationalRate(rotationSpeed));
        
                }else{
                    m_swerve.setControl(drive.withRotationalRate(rotationSpeed));
                }
                
                
                
                
               
        }
        public static Pose2d xySpeed(double x, double y, double maxSpeed) {
            double currentSpeed = Math.hypot(x, y);
    
                 if (currentSpeed > maxSpeed && currentSpeed > 0) {
                    double scale = maxSpeed / currentSpeed;
                    x *= scale;
                    y *= scale;
    }
    
    return new Pose2d(x, y, new Rotation2d(0));
}
}
