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

import frc.robot.subsystems.Limelight.LimelightHelpers;
import frc.robot.subsystems.Limelight.LimelightHelpers.LimelightTarget_Retro;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.Constants.AlignmentConstants;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.RobotContainer;


public class ScoreForSecs extends Command{
  ScorerSubsystem scoreSubsystem;
  private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    
 
  DashboardConfigsSubsystem dashboard;
  
  double m_seconds;
  Timer m_timer = new Timer();
  PIDController drivexPID = new PIDController(AlignmentConstants.pxDrive, AlignmentConstants.ixDrive, AlignmentConstants.dxDrive);
  PIDController driveyPID = new PIDController(AlignmentConstants.pyDrive, AlignmentConstants.iyDrive, AlignmentConstants.dyDrive);
  PIDController rotPID = new PIDController(AlignmentConstants.pRot, AlignmentConstants.iRot, AlignmentConstants.dRot);
  
  
  

  // Constructor
  ScorerSubsystem m_swerveSubsystem;
  public ScoreForSecs(double seconds,DashboardConfigsSubsystem m_dashboard,ScorerSubsystem m_scorer) {
       
    
    dashboard = m_dashboard;
    m_seconds = seconds;
    scoreSubsystem = m_scorer;
    addRequirements(scoreSubsystem);
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
    
    
    
  }
 

 
  
 @Override
  // Actual command
  public void execute() {
    scoreSubsystem.score();

     
}
    
    
  


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    scoreSubsystem.stopIndex();
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    
    return m_timer.hasElapsed(m_seconds);
}
}
    


    