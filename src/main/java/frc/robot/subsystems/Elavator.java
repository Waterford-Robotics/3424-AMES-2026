package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Configs.ElavatorConfigs;
import frc.robot.Constants.MotorIDConstants;

public class Elavator {
    
    





  private TalonFX m_elavatorLeft;
  private TalonFX m_elavatorRight;
  private double m_speedofMotors=0;

  public void ElavatorSubsystem() {
    m_elavatorLeft = new TalonFX(MotorIDConstants.k_elavatorLeftKrakenID);
    m_elavatorRight = new TalonFX(MotorIDConstants.k_elavatorRightKrakenID);    
  
   
    m_elavatorLeft.getConfigurator().apply(ElavatorConfigs.ELAVATOR_LEFT_TALON_FX_CONFIGURATION, 0.05);
    m_elavatorRight.getConfigurator().apply(ElavatorConfigs.ELAVATOR_Right_TALON_FX_CONFIGURATION, 0.5);

  
  }
  public double elavatorSpeedCalculator (double currentRotation, double targetRotations){

    return m_speedofMotors;
  }

  public void elavatorUp(double speed) {
    m_elavatorLeft.set(speed*-1); 
    m_elavatorRight.set(speed); 
    
  }
   public void elavatorDown(double speed) {
    m_elavatorLeft.set(speed); 
    m_elavatorRight.set(speed*-1); 
    
  }
  

  public void stopElavator() {
    m_elavatorLeft.set(0);
    m_elavatorRight.set(0);
  }
}

