package frc.robot.commands;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.DashboardConfigsSubsystem;



public class AilgnShelf2 extends Command{
  CommandSwerveDrivetrain swerveSubsystem;
  private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    
 
  DashboardConfigsSubsystem dashboard;
  
  double m_seconds;
  Timer m_timer = new Timer();
  
  
  
  
  

  // Constructor
  CommandSwerveDrivetrain m_swerveSubsystem;
  public AilgnShelf2(double seconds,DashboardConfigsSubsystem m_dashboard,CommandSwerveDrivetrain m_swerveSubsystem) {
       
    
    dashboard = m_dashboard;
    m_seconds = seconds;
    swerveSubsystem = m_swerveSubsystem;
    addRequirements(swerveSubsystem);
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    
    
    
  }
  
 
  
 @Override
  // Actual command
  public void execute() {
    double driveSpeed;
    driveSpeed=(m_seconds-(m_seconds/ m_timer.get()))*TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    
    swerveSubsystem.setControl(drive.withVelocityY(driveSpeed));

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
    


    

