package frc.robot.subsystems.Limelight;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AlignSubsystem {

    
    static CommandSwerveDrivetrain swerveSubsystem;


    public static void Align(CommandSwerveDrivetrain m_swerve){
        
        Pose3d postions[] ={Constants.AlignmentConstants.shelf1};
        Pose3d target=postions[1];
        Pose3d current = VisionSubsystem.getBotPose();
        double x = current.getX()-target.getX();
   

}
}
