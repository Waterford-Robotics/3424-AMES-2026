package frc.robot.subsystems;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Configs.ScorerConfigs;
import frc.robot.Constants.MotorIDConstants;

public class ScorerSubsystem extends SubsystemBase{

    private TalonFX m_socrer;
    private TalonFX m_socrer2;
    private TalonFX m_indexer;
  
  public ScorerSubsystem() {
    m_socrer = new TalonFX(MotorIDConstants.k_scorerKrakenID,"drivebase");
    m_indexer = new TalonFX(MotorIDConstants.k_indererKrakenID,"drivebase");
    m_socrer2 = new TalonFX(30,"drivebase");
    
  
   
    m_socrer.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.05);
    m_indexer.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.05);
    m_socrer2.getConfigurator().apply(ScorerConfigs.SCORER_TALON_FX_CONFIGURATION, 0.5);
    SmartDashboard.putNumber("Score Speed",0.1);
    

  
  }
  public void scoreinverse(){
     m_indexer.set(-Constants.ScorerConstants.indexSpeed);
  }
  

  public void score() {
    m_socrer.set(Constants.ScorerConstants.scoreSpeed);
    


    
    
  }
  public void index(){
    m_indexer.set(SmartDashboard.getNumber("Score Speed", 0.1));
    m_socrer2.set(-SmartDashboard.getNumber("Score Speed", 0.1));
  }
  

  public void stopIndex() {
    m_socrer.set(0);
     m_indexer.set(0);
     m_socrer2.set(0);
   
  }
}
