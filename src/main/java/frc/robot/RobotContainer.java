// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.lang.ModuleLayer.Controller;

import javax.swing.text.ElementIterator;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.AlignCommand;
import frc.robot.commands.elavatorLevelHL;
import frc.robot.commands.elavatorLevelPB1;
import frc.robot.commands.elavatorLevelPB2;
import frc.robot.commands.elavatorLevelPB3;
import frc.robot.commands.elavatorLevelShelf;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlignSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.DashboardConfigsSubsystem;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import com.pathplanner.lib.events.EventTrigger;
// import frc.robot.subsystems.IntakeSubsystem; how to import
import frc.robot.subsystems.Elavator;


public class RobotContainer extends SubsystemBase{
	private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
	private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

	/* Setting up bindings for necessary control of the swerve drive platform */
	private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
					.withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
					.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	
	public final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
	 
	final Elavator m_elevator = new Elavator();
	final AlignSubsystem m_align = new AlignSubsystem();

	private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

	private final CommandXboxController m_driverController = new CommandXboxController(0);
	private final CommandXboxController m_operatorController = new CommandXboxController(1);
	private final CommandXboxController m_devController = new CommandXboxController(2);
	private final DashboardConfigsSubsystem m_dashboard = new DashboardConfigsSubsystem();

	

	private SendableChooser<Command> m_chooser = new SendableChooser<>();

	public RobotContainer() {
			configureBindings();

			// Note that X is defined as forward according to WPILib convention,
			// and Y is defined as to the left according to WPILib convention.
			// m_drivetrain.setDefaultCommand(
			// 		// Drivetrain will execute this command periodically
			// 		m_drivetrain.applyRequest(() ->
			// 			drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
			// 				.withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
			// 				.withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
			// 		)
			// );
			
	//HL Button
	new JoystickButton(m_operatorController.getHID(), ControllerConstants.scoreHLButton)
    .onTrue(
        new elavatorLevelHL(m_elevator, 1.5) // Runs the command for 2.0 seconds
    );
	//PB1 Button
	new JoystickButton(m_operatorController.getHID(), ControllerConstants.scorePB1)
    .onTrue(
        new elavatorLevelPB1(m_elevator, 1.5) // Runs the command for 2.0 seconds
    );
	//PB2 Button
	new JoystickButton(m_operatorController.getHID(), ControllerConstants.scorePB2)
    .onTrue(
        new elavatorLevelPB2(m_elevator, 1.5) // Runs the command for 2.0 seconds
    );
	//PB3 Button
	new JoystickButton(m_operatorController.getHID(), ControllerConstants.scorePB3)
    .onTrue(
        new elavatorLevelPB3(m_elevator, 1.5) // Runs the command for 2.0 seconds
    );
	//Shelf Button
	new JoystickButton(m_operatorController.getHID(), ControllerConstants.scoreShelf)
    .onTrue(
        new elavatorLevelShelf(m_elevator, 1.5) // Runs the command for 2.0 seconds
    );
	//Aign Code
	
	new JoystickButton(m_driverController.getHID(), ControllerConstants.autoAlign)
    .onTrue(
        new RunCommand(() -> m_align.startPathing())
    );
	new JoystickButton(m_driverController.getHID(), ControllerConstants.autoAlign)
	.onFalse(
		new RunCommand(() -> m_align.stopPathing())
	);
	//Manual Elavtor
	new Trigger(() -> m_operatorController.getRawAxis(ControllerConstants.k_rightTrigger) > 0.0314159)
		.whileTrue(
				new RunCommand(() -> m_elevator.elavatorUp(Constants.ElavatorConstants.elavatorSpeed))
		)
		.onFalse(
				new RunCommand(() -> m_elevator.stopElavator())
		);
	new Trigger(() -> m_operatorController.getRawAxis(ControllerConstants.k_leftTrigger) > 0.0314159)
		.whileTrue(
				new RunCommand(() -> m_elevator.elavatorDown(Constants.ElavatorConstants.elavatorSpeed))
		)
		.onFalse(
				new RunCommand(() -> m_elevator.stopElavator())
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
		// RobotModeTriggers.disabled().whileTrue(
		// 		m_drivetrain.applyRequest(() -> idle).ignoringDisable(true)
		// );

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
