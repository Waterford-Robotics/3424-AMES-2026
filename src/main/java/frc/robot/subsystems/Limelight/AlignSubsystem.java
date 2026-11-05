package frc.robot.subsystems.Limelight;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.AlignmentConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignSubsystem extends SubsystemBase{

    
    static CommandSwerveDrivetrain swerveSubsystem;
    
    


    public static void Align(CommandSwerveDrivetrain m_swerve,PIDController drivexPID,PIDController driveyPID,PIDController rotPID){
        final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
        
        
        Pose3d postions[] ={Constants.AlignmentConstants.shelf1};
        Pose3d target=postions[0];
        double x;
        double y;
        double rotationSpeed=rotPID.calculate(m_swerve.getGyro(),VisionConstants.targetAngle[1]);
        x=drivexPID.calculate(VisionSubsystem.getBotPose().getX(),target.getX());
        y=driveyPID.calculate(VisionSubsystem.getBotPose().getY(),target.getY());
        double ratio = 4/Math.hypot(x, y);
        if (Math.hypot(x, y)>4){
            x*=ratio;
            y*=ratio;
        }
        if(RobotContainer.tolerant(m_swerve.getGyro(), 0.05, VisionConstants.targetAngle[1]))
        {

        m_swerve.setControl(drive.withVelocityY(y).withVelocityX(x).withRotationalRate(rotationSpeed));

        }else{
            m_swerve.setControl(drive.withRotationalRate(rotationSpeed));
        }

}
}
