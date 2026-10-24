package frc.robot.commands;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.ElavatorConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Elavator;

public class AlignCommand {
    
  CommandSwerveDrivetrain drivetrain;
  double m_seconds;
  Timer m_timer = new Timer();
  

  // Constructor
  public AlignCommand(CommandSwerveDrivetrain m_swerveSubsystem, double seconds) {
       
    drivetrain = m_swerveSubsystem;
    m_seconds = seconds;
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
  }
 
  // Actual command
  public void execute() {

    drivetrain.getPose();
      
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    //return (sensor.hex_bore_enecoder==ElavatorConstants.targetRotHL);
    return m_timer.hasElapsed(m_seconds);
  }
}
    


    

