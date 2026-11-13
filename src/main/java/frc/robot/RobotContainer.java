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
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.DashboardConfigsSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.ScorerSubsystem;
import frc.robot.subsystems.Limelight.AlignSubsystem;
import frc.robot.subsystems.Limelight.VisionSubsystem;


public class RobotContainer {
	private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
	private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

	// /* Setting up bindings for necessary control of the swerve drive platform */
	private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
					.withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
					.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	
	public final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
	 
	final Elevator m_elevator = new Elevator();
	

	private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

	private final CommandXboxController m_driverController = new CommandXboxController(0);
	private final CommandXboxController m_operatorController = new CommandXboxController(1);
	private final CommandXboxController m_devController = new CommandXboxController(2);
	private final DashboardConfigsSubsystem m_dashboard = new DashboardConfigsSubsystem();
	private final ScorerSubsystem m_scorerSubsystem = new ScorerSubsystem();
	private final AlignSubsystem m_alignSubsystem = new AlignSubsystem();
	
	

	private SendableChooser<Command> m_chooser = new SendableChooser<>();

	public RobotContainer() {
			configureBindings();

			// Note that X is defined as forward according to WPILib convention,
			// and Y is defined as to the left according to WPILib convention.
			m_drivetrain.setDefaultCommand(
					m_drivetrain.applyRequest(() -> {
						if (!DriverStation.isTeleopEnabled()) {
							return new SwerveRequest.Idle();
						}
						return drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed)
								.withVelocityY(-m_driverController.getLeftX() * MaxSpeed)
								.withRotationalRate(-m_driverController.getRightX() * MaxAngularRate);
					})
			);
			
	
	
	
		

			


			SignalLogger.enableAutoLogging(false);

			
			

			// NamedCommands.registerCommand("ShootAndFlipout", shootAndFlipoutCommandGroup().withTimeout(10));

			//shootAndFlipoutCommandGroup()== a sequential group
			
			
			configureAutonomousChooser();
			



	}

	private void configureAutonomousChooser() {
		SwerveRequest.Idle idle = new SwerveRequest.Idle();
		m_chooser.setDefaultOption("Do Nothing", m_drivetrain.applyRequest(() -> idle));

		// Add every PathPlanner auto file found under src/main/deploy/pathplanner/autos.
		for (String autoName : AutoBuilder.getAllAutoNames()) {
			m_chooser.addOption(autoName, m_drivetrain.getAutonomousCommand(autoName));
		}

		// This project currently has a standalone path, but no PathPlanner .auto files.
		try {
			PathPlannerPath testPath = PathPlannerPath.fromPathFile("New Path");
			Command pathCommand = AutoBuilder.followPath(testPath);
			if (testPath.getStartingHolonomicPose().isPresent()) {
				pathCommand = AutoBuilder.resetOdom(testPath.getStartingHolonomicPose().get())
						.andThen(pathCommand);
			}
			m_chooser.addOption("Path: New Path", pathCommand);
		} catch (Exception e) {
			DriverStation.reportError(
					"Could not load PathPlanner path 'New Path': " + e.getMessage(), e.getStackTrace());
		}

		SmartDashboard.putData("Automode", m_chooser);
	}

	public Command getAutonomousCommand() {
		return m_chooser.getSelected();
	}

	private void configureBindings() {
	//score
	new Trigger(() -> m_driverController.getRawAxis(ControllerConstants.k_rightTrigger) > 0.05)
		.whileTrue(
				new InstantCommand(() -> m_scorerSubsystem.score())
		)
		.onFalse(
				new InstantCommand(() -> m_scorerSubsystem.stopIndex())


		);

//align
	new Trigger(() -> m_driverController.getRawAxis(ControllerConstants.k_leftTrigger) > 0.05)
		.whileTrue(
				new RunCommand(() ->m_alignSubsystem.align(m_drivetrain,VisionSubsystem.getTargetAlign()))
		)
		.onFalse(
				new InstantCommand(() -> m_alignSubsystem.swerveStop(m_drivetrain))
		);
	//reset gyro
	new JoystickButton(m_driverController.getHID(), 7)
    .onTrue(
       new InstantCommand(() -> m_drivetrain.resetGyro())
	);
	//manual elevator
	new JoystickButton(m_operatorController.getHID(), 5)
    .onTrue(
       new InstantCommand(() -> m_elevator.elavatorGo(.3))
	).onFalse(
		new InstantCommand(()-> m_elevator.stopElevator())
	);
	new JoystickButton(m_operatorController.getHID(), 5)
    .onTrue(
       new InstantCommand(() -> m_elevator.elavatorGo(.3))
	).onFalse(
		new InstantCommand(()-> m_elevator.stopElevator())
	);
	//pid control for elevator pegboard 1
	new Trigger(() -> m_operatorController.getRawAxis(ControllerConstants.k_rightTrigger) > 0.05)
		.whileTrue(
				new RunCommand(() ->m_elevator.pidElevatorGo(VisionSubsystem.getTargetAlign()))
		)
		.onFalse(
			new InstantCommand(() ->m_elevator.stopElevator())
		);
	
	
	
	
		// new JoystickButton(m_driverController.getHID(), ControllerConstants.k_leftTrigger)
    // .whileTrue(
    //    new RunCommand(() -> m_alignSubsystem.Align(m_drivetrain, drivexPID, driveyPID, rotPID))
	// ).onFalse(
	// 	new RunCommand(()-> m_scorerSubsystem.stopIndex())
	// );
	// new JoystickButton(m_operatorController.getHID(), ControllerConstants.pegboard1)
    // .onTrue(
    //    new RunCommand(() -> m_elevator.pegboard1())
	// ).onFalse(
	// 	new RunCommand(()-> m_elevator.rest())
	// );
	// new JoystickButton(m_operatorController.getHID(), ControllerConstants.pegboard2)
    // .onTrue(
    //    new RunCommand(() -> m_elevator.pegboard2())
	// ).onFalse(
	// 	new RunCommand(()-> m_elevator.rest())
	// );
	// new JoystickButton(m_operatorController.getHID(), ControllerConstants.pegboard3)
    // .onTrue(
    //    new RunCommand(() -> m_elevator.pegboard3())
	// ).onFalse(
	// 	new RunCommand(()-> m_elevator.rest())
	// );
	// new JoystickButton(m_operatorController.getHID(), ControllerConstants.shelf)
    // .onTrue(
    //    new RunCommand(() -> m_elevator.shelf())
	// ).onFalse(
	// 	new RunCommand(()-> m_elevator.rest())
	// );
	//    new JoystickButton(m_operatorController.getHID(), ControllerConstants.cell)
    //     .onTrue(
    //        new RunCommand(() -> m_elevator.cell())
	//     ).onFalse(
	//  	   new RunCommand(()-> m_elevator.rest())
	//     );
	// 	new JoystickButton(m_operatorController.getHID(), ControllerConstants.shell)
    //     .onTrue(
    //        new RunCommand(() -> m_elevator.shell())
	//     ).onFalse(
	//  	   new RunCommand(()-> m_elevator.rest())
	//     );
	
	
			
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

	public static boolean tolerant(double input,double tolerance,double targetNumber){
		return Math.abs(input - targetNumber) <= tolerance;
	}	
	
}
