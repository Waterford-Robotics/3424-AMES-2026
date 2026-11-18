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
import frc.robot.subsystems.ScorerSubsystem;
import frc.robot.subsystems.DashboardConfigsSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Limelight.LimelightHelpers;
import frc.robot.subsystems.Limelight.LimelightHelpers.LimelightTarget_Retro;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.Constants.AlignmentConstants;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Elevator;


public class zeroCommand extends Command{
  Elevator ElevatorSubsystem;
  private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    
 
  DashboardConfigsSubsystem dashboard;
  
  double m_seconds;
  Timer m_timer = new Timer();
  Timer m_StallCounter=new Timer();
  
  

  // Constructor
  ScorerSubsystem m_swerveSubsystem;
  public zeroCommand(double seconds,DashboardConfigsSubsystem m_dashboard,Elevator m_elevator) {
       
    
    dashboard = m_dashboard;
    m_seconds = seconds;
    ElevatorSubsystem = m_elevator;
    addRequirements(ElevatorSubsystem);
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    
    
    
  }
 

 
  
 @Override
  // Actual command
  public void execute() {
    ElevatorSubsystem.elavatorGo(-0.2);
    if(!(ElevatorSubsystem.getSpeed()<0.1)){
        m_StallCounter.restart();
    }

     
}
    
    
  


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    ElevatorSubsystem.stopElevator();
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    
    return m_StallCounter.hasElapsed(0.2);
}
}
    


    