// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Shoots - just runs shooter
// Clean
package frc.robot.commands;

import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ShootForSecsCommand;

public class RevAndShootCommand extends Command {

  ShooterSubsystem m_shooterSubsystem;
  IndexSubsystem m_indexSubsystem;
  double m_seconds = 1.0;
  Timer m_timer = new Timer();

  // Constructor
  public RevAndShootCommand(ShooterSubsystem shooterSubsystem, IndexSubsystem indexSubsystem) {
        
    // Definitions and setting parameters are equal to members!
    m_shooterSubsystem = shooterSubsystem;
    m_indexSubsystem = indexSubsystem;
    addRequirements(shooterSubsystem, indexSubsystem);
  }

  // Reset timer when the command starts executing
  public void initialize() {
    m_timer.start();
    m_timer.reset();
  }
  
  // Actual command
  public void execute() {
    m_shooterSubsystem.shoot();
    if(m_timer.hasElapsed(m_seconds)){
        m_indexSubsystem.index();
    }
  }

  // Stuff that happens when command is over
  public void end(boolean interrupted) {
    m_shooterSubsystem.stopShooter();
    m_indexSubsystem.stopIndex();
    m_timer.reset();
  }

  // Checks if the command is done
  public boolean isFinished() {
    return false;
    // Am I done?  Am I done? Am I finally done?
  }
}
