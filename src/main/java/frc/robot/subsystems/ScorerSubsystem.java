package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.Configs.ScorerConfigs;
import frc.robot.Constants.MotorIDConstants;

public class ScorerSubsystem {

    private TalonFX m_socrer;
  
  public void ElavatorSubsystem() {
    m_socrer = new TalonFX(MotorIDConstants.k_scorerKrakenID);
    
  
   
    m_socrer.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.05);
    

  
  }
  

  public void elavatorUp() {
    m_socrer.set(Constants.ScorerConstants.scoreSpeed); 
    
    
  }
  

  public void stopIndex() {
    m_socrer.set(0);
   
  }
}
