package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.subsystems.Elavator;
import frc.robot.subsystems.ScorerSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class scoreCommand extends Command{
    



  ScorerSubsystem m_scorer;
  double m_seconds;
  Timer m_timer = new Timer();
  PIDController pid = new PIDController(ElavatorConstants.kP, ElavatorConstants.kI, ElavatorConstants.kD);


  // Constructor
  public scoreCommand(ScorerSubsystem scorerSubsystem, double seconds) {
       
   
    m_scorer = scorerSubsystem;
    m_seconds = seconds;
    
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
  }
 
  // Actual command
  public void execute() {
      m_scorer.score();
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    m_scorer.stopscore();
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    //return (sensor.hex_bore_enecoder==ElavatorConstants.targetRotShelf)
    return m_timer.hasElapsed(m_seconds);
  }
    
}

    

