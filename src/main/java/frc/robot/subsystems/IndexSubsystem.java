package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.IndexerConfigs;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.MotorIDConstants;

public class IndexSubsystem extends SubsystemBase{

  private TalonFX m_shooterIndexer;
  private TalonFX m_beltFloor;

  public IndexSubsystem() {
    m_shooterIndexer = new TalonFX(MotorIDConstants.k_shooterIndexerKrakenID);
    m_beltFloor = new TalonFX(MotorIDConstants.k_beltFloorKrakenID);    
  
   
    m_shooterIndexer.getConfigurator().apply(IndexerConfigs.SHOOTER_INDEXER_TALON_FX_CONFIGURATION, 0.05);
    m_beltFloor.getConfigurator().apply(IndexerConfigs.BELT_FLOOR_TALON_FX_CONFIGURATION, 0.05);

  
  }

  public void index() {
    m_shooterIndexer.set(IndexerConstants.k_shooterIndexerSpeed); // feeds the balls into the shooter
    m_beltFloor.set(IndexerConstants.k_beltFloorSpeed); // conveys balls to shooter
    // TO DO: Check for polarity
  }

  public void stopIndex() {
    m_shooterIndexer.set(0);
    m_beltFloor.set(0);
  }
}