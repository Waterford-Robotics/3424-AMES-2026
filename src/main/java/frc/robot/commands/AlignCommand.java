package frc.robot.commands;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.generated.TunerConstants;
// import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.DashboardConfigsSubsystem;

import frc.robot.subsystems.Limelight.LimelightHelpers;
import frc.robot.subsystems.Limelight.LimelightHelpers.LimelightTarget_Retro;
import frc.robot.RobotContainer;
import frc.robot.Constants.AlignmentConstants;


public class AlignCommand extends Command{
    
  // CommandSwerveDrivetrain drivetrain;
  DashboardConfigsSubsystem dashboard;
  
  double m_seconds;
  Timer m_timer = new Timer();
  // private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric();
  

  // Constructor
  //CommandSwerveDrivetrain m_swerveSubsystem
  public AlignCommand(double seconds,DashboardConfigsSubsystem m_dashboard) {
       
    // drivetrain = m_swerveSubsystem;
    dashboard = m_dashboard;
    m_seconds = seconds;
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    // addRequirements(drivetrain);
    
  }
  
 
  // Actual command
  public void execute() {
    Pose3d robotPose =
    LimelightHelpers.getBotPose3d_TargetSpace("limelight");

    double x = robotPose.getX();
    double y = robotPose.getY();
    double z = robotPose.getZ();
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    
    return m_timer.hasElapsed(m_seconds);
  }
}
    


    

