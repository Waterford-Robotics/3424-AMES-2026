// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Intakes!
// Clean!

package frc.robot.commands;


import frc.robot.Constants.IntakeFlipoutConstants;
import frc.robot.subsystems.IntakeFlipoutSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class ZeroIntakeFlipoutCommand extends Command {


  // Uses Elevator and Subsystems
  IntakeFlipoutSubsystem m_intakeFlipoutSubsystem;
  boolean m_finished;
  Timer m_timer = new Timer();


  // Constructor
  public ZeroIntakeFlipoutCommand(IntakeFlipoutSubsystem intakeFlipoutSubsystem) {
       
    // Definitions and setting parameters are equal to members!
    m_intakeFlipoutSubsystem = intakeFlipoutSubsystem;
    addRequirements(intakeFlipoutSubsystem);
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_finished = false;

    m_timer.start();
    m_timer.reset();
  }
 
  // Actual command
  public void execute() {
     if(m_intakeFlipoutSubsystem.getCurrentPosition() < 0.25 && m_intakeFlipoutSubsystem.getCurrentVelocity() == 0) {
        m_finished = true;
     }
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    m_intakeFlipoutSubsystem.setNeutral();
    m_intakeFlipoutSubsystem.resetSensorPosition(IntakeFlipoutConstants.k_intakeFlipoutHomeAngle);
  }


  // Checks if the command is done
  public boolean isFinished() {
    return m_finished;
  }
}