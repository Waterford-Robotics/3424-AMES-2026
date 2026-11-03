package frc.robot.subsystems;

import java.time.chrono.MinguoDate;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Configs.ScorerConfigs;
import frc.robot.Constants.MotorIDConstants;

public class ScorerSubsystem extends SubsystemBase{

    private TalonFX m_socrer;
    private TalonFX m_indexer;
  
  public void ElavatorSubsystem() {
    m_socrer = new TalonFX(MotorIDConstants.k_scorerKrakenID);
    m_indexer = new TalonFX(MotorIDConstants.k_indererKrakenID);
    
  
   
    m_socrer.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.05);
    m_indexer.getConfigurator().apply(ScorerConfigs.INDEXER_TALON_FX_CONFIGURATION,0.05);
    

  
  }
  

  public void score() {
    m_socrer.set(Constants.ScorerConstants.scoreSpeed); 
    
    
  }
  public void scoreIndex(){
    m_indexer.set(0.3);
  }
  

  public void stopscore() {
    m_socrer.set(0);
    m_indexer.set(0);
   
  }
  
}
