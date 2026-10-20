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

public class SetIntakeFlipoutCommand extends Command {


  // Uses Elevator and Subsystems
  IntakeFlipoutSubsystem m_intakeFlipoutSubsystem;
  boolean m_finished;
  Timer m_timer = new Timer();
  String m_position;


  // Constructor
  public SetIntakeFlipoutCommand(IntakeFlipoutSubsystem intakeFlipoutSubsystem, String position) {
       
    // Definitions and setting parameters are equal to members!
    m_intakeFlipoutSubsystem = intakeFlipoutSubsystem;
    m_position = position;
    addRequirements(intakeFlipoutSubsystem);
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
  }
 
  // Actual command
  public void execute() {
     if (m_position.equals("INTAKE")) {
        m_intakeFlipoutSubsystem.setPosition(IntakeFlipoutConstants.k_intakeFlipoutIntakeAngle);
     }

     if (m_position.equals("HOME")) {
        m_intakeFlipoutSubsystem.setPosition(IntakeFlipoutConstants.k_intakeFlipoutHomeAngle);
     }
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
  }


  // Checks if the command is done
  public boolean isFinished() {
    return m_finished;
  }
}