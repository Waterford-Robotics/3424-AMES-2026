package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;

import com.ctre.phoenix6.signals.NeutralModeValue;



public class Configs {
  public static final class IntakeConfigs {
    // Intake Kraken x44
    public static final TalonFXConfiguration RIGHT_INTAKE_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
    public static final TalonFXConfiguration LEFT_INTAKE_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
    public static final TalonFXConfiguration FLIPOUT_TALON_FX_CONFIGURATION = new TalonFXConfiguration();


    static{
      /*
        ********************************************
        **    INTAKE KRAKEN x44 CONFIGURATIONS    **
        ********************************************
      */

      RIGHT_INTAKE_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      RIGHT_INTAKE_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle =0.95;
      RIGHT_INTAKE_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      RIGHT_INTAKE_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      RIGHT_INTAKE_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 40;

      LEFT_INTAKE_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      LEFT_INTAKE_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle =0.95;
      LEFT_INTAKE_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      LEFT_INTAKE_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      LEFT_INTAKE_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 40;

      /*
        ********************************************
        **    INTAKE FLIP KRAKEN x60 CONFIGURATIONS    **
        ********************************************
      */
      // FLIPOUT_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      // FLIPOUT_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle =0.95;
      // FLIPOUT_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      // FLIPOUT_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      // FLIPOUT_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 50;  

      // Start with ONLY P. Beware of a very high P value!
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kP = 0.6; // how much "push" you have, more push = quicker
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kI = 0.1; // Corrects accumulated error (9.5 goes to 10 with extra push)
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kD = 0; // Softens P curve, stops oscillations
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kS = 0.4; // overcomes gearbox static friction
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kV = 0.001; // Helps maintain velocity point
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kA = 0; // Helps reach desired acceleration
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.kG = 0.5; // helps overcome force of gravity on arm/elevator/etc
      FLIPOUT_TALON_FX_CONFIGURATION.Slot0.GravityType = GravityTypeValue.Arm_Cosine; //knows you are doing arm, could be elevator

      //TODO: ADD AND TEST MANUAL BACKUP CONFIGS 
    
      FLIPOUT_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      FLIPOUT_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      FLIPOUT_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      FLIPOUT_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      FLIPOUT_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 50;

    }
  }
  
  public static final class ShooterConfigs {
    // Left Shooter Kraken x60
    public static final TalonFXConfiguration SHOOTER_TALON_FX_CONFIGURATION = new TalonFXConfiguration();

    /* 
      ***********************************************
      **  LEFT SHOOTER KRAKEN x60 CONFIGURATIONS  **
      ***********************************************
      */
    static{
      SHOOTER_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.05;
      SHOOTER_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      SHOOTER_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      SHOOTER_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Coast;
      SHOOTER_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 60;
    }
  }
  public static final class IndexerConfigs {

    public static final TalonFXConfiguration BELT_FLOOR_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
    public static final TalonFXConfiguration SHOOTER_INDEXER_TALON_FX_CONFIGURATION = new TalonFXConfiguration();
    public static final TalonFXConfiguration ROLLER_FLOOR_TALON_FX_CONFIGURATION = new TalonFXConfiguration();

    /* 
    ***********************************************
    **  BELT_FLOOR x44 CONFIGURATIONS  **
    ***********************************************
    */
    static{
      BELT_FLOOR_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.5;
      BELT_FLOOR_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      BELT_FLOOR_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      BELT_FLOOR_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      BELT_FLOOR_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 30;
      /*
       ********************************************
       **    SHOOTER ROLLER KRAKEN x44 CONFIGURATIONS    **
       ********************************************
      */

      SHOOTER_INDEXER_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.5;
      SHOOTER_INDEXER_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      SHOOTER_INDEXER_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      SHOOTER_INDEXER_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      SHOOTER_INDEXER_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 50;
      /*
        ********************************************
        **    HOPPER ROLLER KRAKEN x44 CONFIGURATIONS    **
        ********************************************
      */

      ROLLER_FLOOR_TALON_FX_CONFIGURATION.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 0.5;
      ROLLER_FLOOR_TALON_FX_CONFIGURATION.MotorOutput.PeakForwardDutyCycle = 0.95;
      ROLLER_FLOOR_TALON_FX_CONFIGURATION.MotorOutput.PeakReverseDutyCycle = -0.95;
      ROLLER_FLOOR_TALON_FX_CONFIGURATION.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      ROLLER_FLOOR_TALON_FX_CONFIGURATION.CurrentLimits.SupplyCurrentLimit = 40;
    }
  }
}