package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;
import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Configs.ElavatorConfigs;
import frc.robot.Constants.MotorIDConstants;

public class Elevator extends SubsystemBase{
  
  private TalonFX m_elavatorLeft;
  private TalonFX m_elavatorRight;
  private PIDController pid = new PIDController(Constants.ElavatorConstants.kP, Constants.ElavatorConstants.kI, Constants.ElavatorConstants.kD);
  
  

  public Elevator() {
    m_elavatorLeft = new TalonFX(MotorIDConstants.k_elavatorLeftKrakenID);
    m_elavatorRight = new TalonFX(MotorIDConstants.k_elavatorRightKrakenID);   
    
   
    m_elavatorLeft.getConfigurator().apply(ElavatorConfigs.ELAVATOR_LEFT_TALON_FX_CONFIGURATION, 0.5);
    m_elavatorRight.getConfigurator().apply(ElavatorConfigs.ELAVATOR_Right_TALON_FX_CONFIGURATION, 0.5);

    SmartDashboard.putNumber("Elevator/P", Constants.ElavatorConstants.kP);
    SmartDashboard.putNumber("Elevator/I", Constants.ElavatorConstants.kI);
    SmartDashboard.putNumber("Elevator/D", Constants.ElavatorConstants.kD);
  }
  

  public void elavatorGo(double speed) {
    m_elavatorLeft.set(speed*-1); 
    m_elavatorRight.set(speed); 
    
  }
  public void pidElevatorGo(int indexHeight) {
    pid.setPID(
        SmartDashboard.getNumber("Elevator/P", Constants.ElavatorConstants.kP),
        SmartDashboard.getNumber("Elevator/I", Constants.ElavatorConstants.kI),
        SmartDashboard.getNumber("Elevator/D", Constants.ElavatorConstants.kD));
    elavatorGo((pid.calculate(getPosition(), Constants.ElavatorConstants.elevatorHeights[indexHeight])));
  }
  public double getPosition() {
        return ((-m_elavatorLeft.getPosition().getValueAsDouble()+m_elavatorRight.getPosition().getValueAsDouble())/2);
    }
  
  

  public void stopElevator() {
    m_elavatorLeft.set(0);
    m_elavatorRight.set(0);
  }
}
