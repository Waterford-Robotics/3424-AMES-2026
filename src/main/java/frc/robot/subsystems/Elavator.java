package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ElavatorConfigs;
import frc.robot.Constants.MotorIDConstants;

public class Elavator extends SubsystemBase{
  
  private TalonFX m_elavatorLeft;
  private TalonFX m_elavatorRight;
  

  public Elavator() {
    m_elavatorLeft = new TalonFX(MotorIDConstants.k_elavatorLeftKrakenID);
    m_elavatorRight = new TalonFX(MotorIDConstants.k_elavatorRightKrakenID);    
  
   
    m_elavatorLeft.getConfigurator().apply(ElavatorConfigs.ELAVATOR_LEFT_TALON_FX_CONFIGURATION, 0.5);
    m_elavatorRight.getConfigurator().apply(ElavatorConfigs.ELAVATOR_Right_TALON_FX_CONFIGURATION, 0.5);

  
  }
  public void random(){}
  //was made to allow me to commoit
  

  public void elavatorUp(double speed) {
    m_elavatorLeft.set(speed*-1); 
    m_elavatorRight.set(speed); 
    
  }
   public void elavatorDown(double speed) {
    m_elavatorLeft.set(speed); 
    m_elavatorRight.set(speed*-1); 
    
  }
  public double getPositionLeft() {
        return -m_elavatorLeft.getPosition().getValueAsDouble();
    }
  public double getPositionRight() {
        return m_elavatorRight.getPosition().getValueAsDouble();
    }
  

  public void stopElavator() {
    m_elavatorLeft.set(0);
    m_elavatorRight.set(0);
  }
}

