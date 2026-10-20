// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.IndexForSecsCommand;
import frc.robot.commands.IntakeForSecsCommand;
import frc.robot.commands.RevAndShootCommand;
import frc.robot.commands.SetIntakeFlipoutCommand;
import frc.robot.commands.ShootForSecsCommand;
import frc.robot.commands.ShootFarForSecsCommand;
import frc.robot.commands.ZeroIntakeFlipoutCommand;



import frc.robot.generated.TunerConstants;


import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeFlipoutSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IndexSubsystem;

public class RobotContainer {
	private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
	private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

	/* Setting up bindings for necessary control of the swerve drive platform */
	private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
					.withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
					.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	
	public final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
	private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
	private final IntakeFlipoutSubsystem m_intakeFlipoutSubsystem = new IntakeFlipoutSubsystem();

	private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
	private final IndexSubsystem m_indexSubsystem = new IndexSubsystem();

	private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

	private final CommandXboxController m_driverController = new CommandXboxController(ControllerConstants.k_driverControllerPort);
	private final CommandXboxController m_operatorController = new CommandXboxController(ControllerConstants.k_operatorControllerPort);

	

	private SendableChooser<Command> m_chooser = new SendableChooser<>();

	public RobotContainer() {
			configureBindings();

			// Note that X is defined as forward according to WPILib convention,
			// and Y is defined as to the left according to WPILib convention.
			m_drivetrain.setDefaultCommand(
					// Drivetrain will execute this command periodically
					m_drivetrain.applyRequest(() ->
						drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
							.withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
							.withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
					)
			);

			// m_drivetrain.setDefaultCommand(
			// 		// Drivetrain will execute this command periodically
			// 		m_drivetrain.applyRequest(() -> (
			// 			if (new JoystickButton(m_driverController.getHID(), ControllerConstants.k_Start) || JoystickButton(m_operatorController.getHID(), ControllerConstants.k_Start)){
			// 				return m_drivetrain.XWheels();
			// 			}
			// 			else {
			// 				drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
			// 				.withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
			// 				.withRotationalRate(-m_driverController.getRightX() * MaxAngularRate); // Drive counterclockwise with negative X (left)
			// 			}
			// 		)
			// 		)
			// );


			SignalLogger.enableAutoLogging(false);

			// NamedCommands.registerCommand("ShootCommand", 
			// 		new ShootForSecsCommand(m_shooterSubsystem,3)
			// );
			NamedCommands.registerCommand("Intake", 
			 		new IntakeForSecsCommand(m_intakeSubsystem, 1.0)
			);

			NamedCommands.registerCommand("ShootAndFlipout", shootAndFlipoutCommandGroup().withTimeout(10));
			NamedCommands.registerCommand("Shoot", shootCommandGroup());
			NamedCommands.registerCommand("flipoutDeploy", flipoutDeploy().withTimeout(8));
			
			//autos!! (only one works)
				m_chooser.addOption("Shoot", Shoot);
				m_chooser.addOption("ShootFar", ShootFar);
				m_chooser.addOption("LSNS", m_drivetrain.getAutonomousCommand("LSNS"));
				m_chooser.addOption("RSNS", m_drivetrain.getAutonomousCommand("RSNS"));
				m_chooser.addOption("test", m_drivetrain.getAutonomousCommand("test"));
				m_chooser.addOption("middleshoot",m_drivetrain.getAutonomousCommand("MiddleShootAuto"));
				m_chooser.addOption("DoubleDipLeftTrench", m_drivetrain.getAutonomousCommand("DoubleDipLeft"));

			new EventTrigger("intakeRollers")
			.whileTrue(new RunCommand(
					() -> m_intakeSubsystem.intake(),m_intakeSubsystem));

			SmartDashboard.putData("Automode", m_chooser);
			



	}
	private void configureBindings() {
			
		// Idle while the robot is disabled. This ensures the configured
		// neutral mode is applied to the drive motors while disabled.
		final var idle = new SwerveRequest.Idle();
		RobotModeTriggers.disabled().whileTrue(
				m_drivetrain.applyRequest(() -> idle).ignoringDisable(true)
		);

		// m_driverController.b().whileTrue(drivetrain.applyRequest(() ->
		//     point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))
		// ));

		// Zero NavX
		new JoystickButton(m_driverController.getHID(), ControllerConstants.k_Start)
		.onTrue(
				new InstantCommand(() -> m_drivetrain.resetGyro(), m_drivetrain)
		);

		// X Wheels
		new JoystickButton(m_operatorController.getHID(), ControllerConstants.k_rightTrigger)
    	.whileTrue(
			new RunCommand(() -> m_drivetrain.XWheels(), m_drivetrain)
		);

		new JoystickButton(m_operatorController.getHID(), ControllerConstants.k_Start)
    	.whileTrue(
			new InstantCommand(() -> m_drivetrain.XWheels(), m_drivetrain)
		);


		// intake wheels
		new Trigger(() -> m_driverController.getRawAxis(ControllerConstants.k_rightTrigger) > 0.05)
		.whileTrue(
				new RunCommand(() -> m_intakeSubsystem.intake())
		)
		.onFalse(
				new RunCommand(() -> m_intakeSubsystem.stopIntake())
		);

		// FLIP IN AND FLIP OUT BUTTON BINDINGS 

		new JoystickButton(m_driverController.getHID(), ControllerConstants.k_rightBumper)
		.onTrue(
			new SetIntakeFlipoutCommand(m_intakeFlipoutSubsystem,"HOME").andThen(
				new ZeroIntakeFlipoutCommand(m_intakeFlipoutSubsystem)
			)
		);

		new JoystickButton(m_driverController.getHID(), ControllerConstants.k_leftBumper)
		.onTrue(
			new ZeroIntakeFlipoutCommand(m_intakeFlipoutSubsystem).andThen(
				new SetIntakeFlipoutCommand(m_intakeFlipoutSubsystem, "INTAKE")
			)
		);

		new JoystickButton(m_driverController.getHID(), ControllerConstants.k_Back)
		.onTrue(
			new ZeroIntakeFlipoutCommand(m_intakeFlipoutSubsystem)
		);

		// new Trigger(() -> m_driverController.getRawAxis(ControllerConstants.k_leftTrigger) > 0.05)
		// .whileTrue(
		// 	new RevAndShootCommand(m_shooterSubsystem, m_indexSubsystem)
		// );


		//BACKUP FLIPOUT MANUAL ON OPERATOR CONBTROLLER
		new JoystickButton(m_operatorController.getHID(), ControllerConstants.k_leftBumper)
		.onTrue(
				new RunCommand(() -> m_intakeFlipoutSubsystem.flipout(), m_intakeFlipoutSubsystem)
		)
		.onFalse(
				new RunCommand(() -> m_intakeFlipoutSubsystem.stopFlipout(), m_intakeFlipoutSubsystem)
		);

		new JoystickButton(m_operatorController.getHID(), ControllerConstants.k_rightBumper)
		.onTrue(
				new RunCommand(() -> m_intakeFlipoutSubsystem.reverseFlipout(), m_intakeFlipoutSubsystem)
		)
		.onFalse(
				new RunCommand(() -> m_intakeFlipoutSubsystem.stopFlipout(), m_intakeFlipoutSubsystem)
		);
		
		// flip out on driver controller - UPDATED
		// new JoystickButton(m_driverController.getHID(), ControllerConstants.k_rightBumper)
		// .whileTrue(
		// 		new RunCommand(() -> m_intakeFlipoutSubsystem.intakeFlip(), m_intakeFlipoutSubsystem)
		// )
		// .onFalse(
		// 		new RunCommand(() -> m_intakeFlipoutSubsystem.stopIntakeFlip(), m_intakeFlipoutSubsystem)
		// );
		

		//indexer runs: belt floor, shooter indexer on Driver controller
		new Trigger(() -> m_driverController.getRawAxis(ControllerConstants.k_leftTrigger) > 0.05)
		.whileTrue(
			new RunCommand(() -> m_indexSubsystem.index())
		)
		.onFalse(
			new RunCommand(() -> m_indexSubsystem.stopIndex())
		);
		
		// rev up normal speed
		new Trigger (() -> m_operatorController.getRawAxis(ControllerConstants.k_rightTrigger) > 0.05)
		.whileTrue(
				new RunCommand(() -> m_shooterSubsystem.shoot())
	
		)
		.onFalse(
				new RunCommand(() -> m_shooterSubsystem.stopShooter())
		);

		

		// rev up faster speed
		new Trigger (() -> m_operatorController.getRawAxis(ControllerConstants.k_leftTrigger) > 0.05)
		.whileTrue(
				new RunCommand(() -> m_shooterSubsystem.farShoot())
		)
		.onFalse(
				new RunCommand(() -> m_shooterSubsystem.stopShooter())
		);

		new JoystickButton(m_operatorController.getHID(), ControllerConstants.k_X)
		.onTrue(
				new RunCommand(() -> m_intakeSubsystem.reverseIntake(), m_intakeSubsystem)
		)
		.onFalse(
				new RunCommand(() -> m_intakeSubsystem.stopIntake(), m_intakeSubsystem)
		);
	}

	//Command Groups for autos, make em named commands
	public SequentialCommandGroup shootAndFlipoutCommandGroup() { 
		return new SequentialCommandGroup(
		new ShootForSecsCommand(m_shooterSubsystem, 0.5),//rev first
		new ParallelCommandGroup(
			new ShootForSecsCommand(m_shooterSubsystem, 5),
			new IndexForSecsCommand(m_indexSubsystem,5)),
		new ZeroIntakeFlipoutCommand(m_intakeFlipoutSubsystem),
		new SetIntakeFlipoutCommand(m_intakeFlipoutSubsystem, "INTAKE")
		);
	}
	//for shooting the second time
	public SequentialCommandGroup shootCommandGroup() {
		return new SequentialCommandGroup(
		new ShootFarForSecsCommand(m_shooterSubsystem, 0.5),
		new ParallelCommandGroup(
			new ShootForSecsCommand(m_shooterSubsystem, 3),
			new IndexForSecsCommand(m_indexSubsystem,3))
		);
	}
	public SequentialCommandGroup flipoutDeploy() {
		return new SequentialCommandGroup(
			new ZeroIntakeFlipoutCommand(m_intakeFlipoutSubsystem),
			new SetIntakeFlipoutCommand(m_intakeFlipoutSubsystem, "INTAKE")
		);
	}

		// Hardcoded Auto--stationary shoot w intake deploy (WORKS)
	SequentialCommandGroup Shoot = new SequentialCommandGroup(
		new ShootForSecsCommand(m_shooterSubsystem, 1),
		new ParallelCommandGroup(
			new ShootForSecsCommand(m_shooterSubsystem, 5),
			new IndexForSecsCommand(m_indexSubsystem,5))
	);

	SequentialCommandGroup ShootFar = new SequentialCommandGroup(
		new ShootFarForSecsCommand(m_shooterSubsystem, 0.5),
		new ParallelCommandGroup(
			new ShootFarForSecsCommand(m_shooterSubsystem, 3),
			new IndexForSecsCommand(m_indexSubsystem,3))
	);




	public Command getAutonomousCommand() {
			return m_chooser.getSelected();
	}
	
}
