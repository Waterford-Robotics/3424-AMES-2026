package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Configs.ScorerConfigs;
import frc.robot.Constants.MotorIDConstants;

public class ScorerSubsystem extends SubsystemBase{

    private TalonFX m_socrer;
    private TalonFX m_indexer;
  
  public ScorerSubsystem() {
    m_socrer = new TalonFX(MotorIDConstants.k_scorerKrakenID,"drivebase");
    m_indexer = new TalonFX(MotorIDConstants.k_indererKrakenID,"drivebase");
    
  
   
    m_socrer.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.05);
    m_indexer.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.05);
    

  
  }
  

  public void score() {
    m_socrer.set(Constants.ScorerConstants.scoreSpeed);
    m_indexer.set(Constants.ScorerConstants.indexSpeed);

    
    
  }
  

  public void stopIndex() {
    m_socrer.set(0);
     m_indexer.set(0);
   
  }
}
