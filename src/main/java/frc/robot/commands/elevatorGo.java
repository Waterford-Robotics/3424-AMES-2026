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
import frc.robot.subsystems.Limelight.VisionSubsystem;
import frc.robot.subsystems.Limelight.LimelightHelpers.LimelightTarget_Retro;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.Constants.AlignmentConstants;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.RobotContainer;


public class elevatorGo extends Command{
  Elevator elevatorSubsystem;
  private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    
 
  double m_seconds;
  Timer m_timer = new Timer();
  int index;
  
  

  // Constructor
  ScorerSubsystem m_swerveSubsystem;
  public elevatorGo(double seconds, Elevator elevator,int indexer) {
       
    
    
    m_seconds = seconds;
    elevatorSubsystem=elevator;
    addRequirements(elevatorSubsystem);
    index=indexer;
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    
    
    
  }
 

 
  
 @Override
  // Actual command
  public void execute() {
    elevatorSubsystem.pidElevatorGo(index);

     
}
    
    
  


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    elevatorSubsystem.stopElevator();
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    
    return m_timer.hasElapsed(m_seconds)||elevatorSubsystem.getPosition()==Constants.ElavatorConstants.elevatorHeights[index];
}
}
    


    