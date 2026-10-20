package frc.robot.subsystems;

import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.IntakeConfigs;
import frc.robot.Constants.MotorIDConstants;
public class IntakeFlipoutSubsystem extends SubsystemBase {

    private TalonFX m_intakeFlipout;
    
    public IntakeFlipoutSubsystem() {

        m_intakeFlipout = new TalonFX(MotorIDConstants.k_flipoutKrakenID); //gets ID number
        m_intakeFlipout.getConfigurator().apply(IntakeConfigs.FLIPOUT_TALON_FX_CONFIGURATION.Slot0, 0.05); //GIVES UP AFTER 0.05 SECONDs
    }

    //ALL METHODS USED FOR PID CONTROL, MANUAL METHODS USED BELOW
    public void setPosition(Angle angle) {
        m_intakeFlipout.setControl(new PositionVoltage(angle.in(Units.Rotations))); // picks angle then converts to voltage command that motor can understand
    }

    
    public void setNeutral() {
        m_intakeFlipout.setControl(new NeutralOut()); // coasts motor, only gravity acts on motor
    }

    public void resetSensorPosition(Angle setpoint) {
        m_intakeFlipout.setPosition(setpoint.in(Units.Rotations)); // Sets encoder value to certain number, usually zero
    }

    public double getCurrentPosition() {
        return m_intakeFlipout.getPosition().getValueAsDouble(); //  pos your arm is at right now in rotations
    }

    public double getCurrentVelocity() {
        return m_intakeFlipout.getVelocity().getValueAsDouble(); // velocity your arm is at right now in rpms
    }

    public void periodic() {
        SmartDashboard.putNumber("Wrist/Pos", Units.Rotations.of(m_intakeFlipout.getPosition().getValueAsDouble()).magnitude()); // puts position on smart dashboard, 14ish 
    }


    //MANUAL FLIPOUT BUTTONS INCASE PID DOESNT WORK!!
    public void flipout() {
        m_intakeFlipout.set(0.2);
    }

    public void reverseFlipout() {
        m_intakeFlipout.set(-0.2);
    }
    public void stopFlipout() {
        m_intakeFlipout.set(0);
    }

}
