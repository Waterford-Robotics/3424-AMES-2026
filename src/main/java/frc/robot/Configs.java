package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;



public class Configs {
  public static final class ElavatorConfigs{
    public static final TalonFXConfiguration ELAVATOR_LEFT_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
    public static final TalonFXConfiguration ELAVATOR_Right_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
      
    static{
      ELAVATOR_Right_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      ELAVATOR_Right_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      ELAVATOR_Right_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      ELAVATOR_Right_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      ELAVATOR_Right_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 50;
      ELAVATOR_LEFT_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      ELAVATOR_LEFT_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      ELAVATOR_LEFT_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      ELAVATOR_LEFT_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      ELAVATOR_LEFT_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 50;
      
    }
  }
  public static final class ScorerConfigs {
    public static final TalonFXConfiguration SCORER_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
    static{
      SCORER_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      SCORER_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle =0.95;
      SCORER_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      SCORER_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      SCORER_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 40;
    }
  
    
  }


  //this was a important class but needs to be rewritten by Q 
  //Used for kraken configs
  
}