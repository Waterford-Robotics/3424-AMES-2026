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




import frc.robot.generated.TunerConstants;


import frc.robot.subsystems.CommandSwerveDrivetrain;
// import frc.robot.subsystems.IntakeSubsystem; how to import


public class RobotContainer {
	private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
	private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

	/* Setting up bindings for necessary control of the swerve drive platform */
	private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
					.withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
					.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	
	public final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
	//private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(); this is what you need to do for each subsystem
	

	private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

	private final CommandXboxController m_driverController = new CommandXboxController(0);
	private final CommandXboxController m_operatorController = new CommandXboxController(1);
	private final CommandXboxController m_devController = new CommandXboxController(2);

	

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

			
			

			// NamedCommands.registerCommand("ShootAndFlipout", shootAndFlipoutCommandGroup().withTimeout(10));

			//shootAndFlipoutCommandGroup()== a sequential group
			
			
			//autos!! (only one works)
				// m_chooser.addOption("Shoot", Shoot);
				

			// new EventTrigger("intakeRollers")
			// .whileTrue(new RunCommand(
			// 		() -> m_intakeSubsystem.intake(),m_intakeSubsystem));
// I thought lines above might be useful 
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

		

		
	}

	//Command Groups for autos, make em named commands
	// public SequentialCommandGroup shootAndFlipoutCommandGroup() { 
	// 	return new SequentialCommandGroup(
	// 	new ShootForSecsCommand(m_shooterSubsystem, 0.5),//rev first
	// 	new ParallelCommandGroup(
	// 		new ShootForSecsCommand(m_shooterSubsystem, 5),
	// 		new IndexForSecsCommand(m_indexSubsystem,5)),
	// 	new ZeroIntakeFlipoutCommand(m_intakeFlipoutSubsystem),
	// 	new SetIntakeFlipoutCommand(m_intakeFlipoutSubsystem, "INTAKE")
	// 	);
	// }
	
	
}
