package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.subsystems.Elavator;

public class elavatorLevelPB3 {
    Elavator m_Elavator;
  double m_seconds;
  Timer m_timer = new Timer();
  PIDController pid = new PIDController(ElavatorConstants.kP, ElavatorConstants.kI, ElavatorConstants.kD);


  // Constructor
  public elavatorLevelPB3(Elavator elavatorSubsystem, double seconds) {
       
   
    m_Elavator = elavatorSubsystem;
    m_seconds = seconds;
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
  }
 
  // Actual command
  public void execute() {
      m_Elavator.elavatorUp(pid.calculate(0, ElavatorConstants.targetRotPB3));
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    m_Elavator.stopElavator();
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    return m_timer.hasElapsed(m_seconds);
  }
    
}
