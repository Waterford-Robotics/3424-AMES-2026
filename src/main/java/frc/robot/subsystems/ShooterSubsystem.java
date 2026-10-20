package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Constants.MotorIDConstants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase { 

  private TalonFX m_leftShooter; // lower shooter
  private TalonFX m_rightShooter; // upper shooter
  

  public ShooterSubsystem () {
    //kraken x60s
    m_leftShooter = new TalonFX(MotorIDConstants.k_leftshooterKrakenID);
    m_rightShooter = new TalonFX(MotorIDConstants.k_rightshooterKrakenID);

    m_leftShooter.getConfigurator().apply(ShooterConfigs.SHOOTER_TALON_FX_CONFIGURATION, 0.05);
    m_rightShooter.getConfigurator().apply(ShooterConfigs.SHOOTER_TALON_FX_CONFIGURATION, 0.05);
  }

  public void shoot() {
    m_leftShooter.set(-ShooterConstants.k_shooterSpeed);  // Revs up the two shooter motors
    m_rightShooter.set(ShooterConstants.k_shooterSpeed);
    
  }

  public void farShoot() {
    m_leftShooter.set(-1.3*ShooterConstants.k_shooterSpeed);  // Revs up the two shooter motors
    m_rightShooter.set(1.3*ShooterConstants.k_shooterSpeed);
    
  }

  public void stopShooter() {
    m_leftShooter.set(0);  // Stops the two shooter motors
    m_rightShooter.set(0);
  }
}