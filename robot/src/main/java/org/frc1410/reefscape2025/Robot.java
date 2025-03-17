package org.frc1410.reefscape2025;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.reefscape2025.commands.Drivetrain.AutoAlign;
import org.frc1410.reefscape2025.commands.Drivetrain.DriveLooped;
import org.frc1410.reefscape2025.commands.Drivetrain.ToggleSlowmode;
import org.frc1410.reefscape2025.commands.Elevator.*;
import org.frc1410.reefscape2025.commands.Elevator.Actions.*;
import org.frc1410.reefscape2025.commands.Elevator.Manual.SetCoralRotationManual;
import org.frc1410.reefscape2025.commands.LEDCommand;
import org.frc1410.reefscape2025.commands.AutonomousCommands.AutonomousScoring;
import org.frc1410.reefscape2025.commands.Lbozo.IntakeCoral;
import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;
import org.frc1410.reefscape2025.subsystems.SuperStructure;
import org.frc1410.reefscape2025.util.NetworkTables;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import static org.frc1410.reefscape2025.util.IDs.*;

import org.ejml.equation.IntegerSequence.For;

import static org.frc1410.reefscape2025.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.2);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final Elevator elevator = subsystems.track(new Elevator());
	private final CoralRotation coralRotation = subsystems.track(new CoralRotation());
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain(this.subsystems));
	private final LBozo lBozo = subsystems.track(new LBozo());
	private final LEDs leds = subsystems.track(new LEDs());

	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = this.nt.getTable("Auto");

	private final AutoSelector autoSelector = new AutoSelector()
			.add("2", () -> new PathPlannerAuto("2"));

			 {
				{
		var profiles = new String[this.autoSelector.getProfiles().size()];
		for (var i = 0; i < profiles.length; i++) {
			profiles[i] = this.autoSelector.getProfiles().get(i).name();
		}

		var autoChoicesPub = NetworkTables.PublisherFactory(this.table, "Choices", profiles);
		autoChoicesPub.accept(profiles);
		}
	}

	public Robot() {
		
		AutoBuilder.configure(
				this.drivetrain::getEstimatedPosition,
				this.drivetrain::resetPose,
				this.drivetrain::getChassisSpeeds,
				this.drivetrain::drive,
				HOLONOMIC_AUTO_CONFIG,
				ROBOT_CONFIG,
				() -> {
					var alliance = DriverStation.getAlliance();

					if(alliance.isPresent()) {
						return alliance.get() == DriverStation.Alliance.Red;
					}
					return false;
				},
				drivetrain
		);

		NamedCommands.registerCommand("ScoreL4", new AutonomousScoring(elevator, coralRotation, lBozo, leds, SuperStructure.L4));
		NamedCommands.registerCommand("ScoreL3", new AutonomousScoring(elevator, coralRotation, lBozo, leds, SuperStructure.L3));
		NamedCommands.registerCommand("Intake", new IntakeCoral(elevator, coralRotation, lBozo, leds));
		NamedCommands.registerCommand("Outtake", new OuttakeCoral(lBozo, leds, true));
	}

	private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(this.table, "Profile",
			this.autoSelector.getProfiles().isEmpty() ? "0" : this.autoSelector.getProfiles().get(0).name());

	private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(this.table, this.autoPublisher.getTopic());

	@Override
	public void autonomousSequence() {
		NetworkTables.SetPersistence(this.autoPublisher.getTopic(), true);
			String autoProfile = this.autoSubscriber.get();
			var autoCommand = this.autoSelector.select(autoProfile);

			this.scheduler.scheduleAutoCommand(autoCommand);
	}

	@Override
	public void teleopSequence() {
		this.operatorController.RIGHT_STICK.whenPressed(new InstantCommand(drivetrain::playMusic), TaskPersistence.GAMEPLAY);
		this.operatorController.RIGHT_TRIGGER.button().whileHeldOnce(new IntakeCoral(elevator, coralRotation, lBozo, leds), TaskPersistence.GAMEPLAY);
		this.operatorController.LEFT_TRIGGER.button().whileHeldOnce(new OuttakeCoral(lBozo, leds, true), TaskPersistence.GAMEPLAY);
		this.driverController.RIGHT_TRIGGER.button().whileHeldOnce(new OuttakeCoral(lBozo, leds, true), TaskPersistence.GAMEPLAY, LockPriority.HIGHEST);

		// this.scheduler.scheduleDefaultCommand(new ElevatorManual(elevator, this.operatorController.LEFT_Y_AXIS), TaskPersistence.GAMEPLAY);
		this.operatorController.Y.whenPressed(new ConfigureLevel(elevator, coralRotation, SuperStructure.L4), TaskPersistence.GAMEPLAY);
		this.operatorController.B.whenPressed(new ConfigureLevel(elevator, coralRotation, SuperStructure.L3), TaskPersistence.GAMEPLAY);
		this.operatorController.A.whenPressed(new ConfigureLevel(elevator, coralRotation, SuperStructure.L2), TaskPersistence.GAMEPLAY);
		this.operatorController.X.whenPressed(new ConfigureLevel(elevator, coralRotation, SuperStructure.L1), TaskPersistence.GAMEPLAY);

		//NUH UH

		//this.operatorController.DPAD_RIGHT.whenPressed(new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.CORAL_OFF, leds), TaskPersistence.GAMEPLAY);

		this.operatorController.DPAD_DOWN.whenPressed(new ConfigureLevelSimultanious(elevator, coralRotation, SuperStructure.HOME), TaskPersistence.GAMEPLAY);
		
		//this.operatorController.RIGHT_BUMPER.whenPressed(new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.INTAKE, leds), TaskPersistence.GAMEPLAY);

		this.scheduler.scheduleDefaultCommand(new HoldElevatorPID(elevator, coralRotation), TaskPersistence.GAMEPLAY);
//		this.scheduler.scheduleDefaultCommand(new ElevatorManual(elevator, driverController.RIGHT_Y_AXIS), TaskPersistence.GAMEPLAY);

		this.scheduler.scheduleDefaultCommand(new DriveLooped(
						this.drivetrain,
						this.elevator,
						this.driverController.LEFT_Y_AXIS,
						this.driverController.LEFT_X_AXIS,
						this.driverController.RIGHT_X_AXIS,
						this.driverController.LEFT_TRIGGER
					), TaskPersistence.EPHEMERAL, LockPriority.HIGH
		);


		this.operatorController.START.whileHeldOnce(new SetCoralRotationManual(coralRotation, 0.2), TaskPersistence.GAMEPLAY);
		this.operatorController.LEFT_STICK.whileHeldOnce(new SetCoralRotationManual(coralRotation, -0.1), TaskPersistence.GAMEPLAY);

		this.operatorController.BACK.whenPressed(new ResetEncoders(elevator), TaskPersistence.GAMEPLAY);

		this.driverController.A.whenPressed(new ToggleSlowmode(drivetrain), TaskPersistence.GAMEPLAY);

		this.driverController.Y.whenPressed(new InstantCommand(
				() -> {
					if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
						this.drivetrain.setYaw(Rotation2d.fromDegrees(180));
					} else {
						this.drivetrain.setYaw(Rotation2d.fromDegrees(0));
					}
				}
				), TaskPersistence.GAMEPLAY
		);

		this.driverController.RIGHT_BUMPER.whileHeldOnce(new AutoAlign(
				this.drivetrain,
						true
				), TaskPersistence.GAMEPLAY
		);

		this.driverController.LEFT_BUMPER.whileHeldOnce(new AutoAlign(
				this.drivetrain,
				false
				), TaskPersistence.GAMEPLAY
		);

		this.scheduler.scheduleDefaultCommand(new LEDCommand(leds, elevator, lBozo), TaskPersistence.DURABLE);
	}

	@Override
	public void testSequence() {
		
	}

	@Override
	protected void disabledSequence() {}
}
