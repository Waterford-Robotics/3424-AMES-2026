package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.IntakeConfigs;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.MotorIDConstants;

// Clean
public class IntakeSubsystem extends SubsystemBase {

  private TalonFX m_rightIntake; // intake wheel ADD NEW
  private TalonFX m_leftIntake;

  public IntakeSubsystem() {
    m_rightIntake = new TalonFX(MotorIDConstants.k_rightIntakeKrakenID);
    m_rightIntake.getConfigurator().apply(IntakeConfigs.RIGHT_INTAKE_TALON_FX_CONFIGURATION, 0.05);
    m_leftIntake = new TalonFX(MotorIDConstants.k_leftIntakeKrakenID);
    m_leftIntake.getConfigurator().apply(IntakeConfigs.LEFT_INTAKE_TALON_FX_CONFIGURATION, 0.05);
  }


  public void intake() { // -  +
    m_rightIntake.set(-IntakeConstants.k_intakeSpeed);//intake forward
    m_leftIntake.set(IntakeConstants.k_intakeSpeed);//intake forward
  }


  public void reverseIntake() {
    m_rightIntake.set(IntakeConstants.k_intakeSpeed); //make it run backwards!
    m_leftIntake.set(-IntakeConstants.k_intakeSpeed);
  }

  public void stopIntake() {
    m_rightIntake.set(0);
    m_leftIntake.set(0);
  }
}