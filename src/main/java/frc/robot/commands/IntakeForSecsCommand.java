// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Intakes!
// Clean!

package frc.robot.commands;


import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeForSecsCommand extends Command {


  // Uses Elevator and Subsystems
  IntakeSubsystem m_intakeSubsystem;
  double m_seconds;
  Timer m_timer = new Timer();


  // Constructor
  public IntakeForSecsCommand(IntakeSubsystem intakeSubsystem, double seconds) {
       
    // Definitions and setting parameters are equal to members!
    m_intakeSubsystem = intakeSubsystem;
    addRequirements(intakeSubsystem);
    m_seconds = seconds;
  }


  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
  }
 
  // Actual command
  public void execute() {
      m_intakeSubsystem.intake();
  }


  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    m_intakeSubsystem.stopIntake();
  }


  // Checks if the command is done
  public boolean isFinished() {
    // Am I done?  Am I done? Am I finally done?
    return m_timer.hasElapsed(m_seconds);
  }
}