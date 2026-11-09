package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ElavatorConfigs;
import frc.robot.Constants;
import frc.robot.Constants.MotorIDConstants;

public class Elevator extends SubsystemBase {

    private final TalonFX m_elavatorLeft;
    private final TalonFX m_elavatorRight;
    private final PIDController pid = new PIDController(
        Constants.ElavatorConstants.kP, 
        Constants.ElavatorConstants.kI, 
        Constants.ElavatorConstants.kD
    );

    public Elevator() {
        m_elavatorLeft = new TalonFX(MotorIDConstants.k_elavatorLeftKrakenID);
        m_elavatorRight = new TalonFX(MotorIDConstants.k_elavatorRightKrakenID);   
        
        m_elavatorLeft.getConfigurator().apply(ElavatorConfigs.ELAVATOR_LEFT_TALON_FX_CONFIGURATION, 0.5);
        m_elavatorRight.getConfigurator().apply(ElavatorConfigs.ELAVATOR_Right_TALON_FX_CONFIGURATION, 0.5);
    }

    public void elavatorGo(double speed) {
        // Drive right motor normally, drive left motor inverted directly in code
        m_elavatorRight.set(speed); 
        m_elavatorLeft.set(-speed); 
    }

    public void pidElevatorGo(int indexHeight) {
        double output = pid.calculate(getPosition(), Constants.ElavatorConstants.elevatorHeights[indexHeight]);
        elavatorGo(output);
    }

    public double getPosition() {
        return m_elavatorRight.getPosition().getValueAsDouble();
    }

    public void stopElevator() {
        m_elavatorRight.set(0.0);
        m_elavatorLeft.set(0.0);
    }
}