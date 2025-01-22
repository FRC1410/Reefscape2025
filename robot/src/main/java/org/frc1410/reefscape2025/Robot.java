package org.frc1410.reefscape2025;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.reefscape2025.commands.AutoAlignToReef;
import org.frc1410.reefscape2025.commands.DriveLooped;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.util.NetworkTables;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {
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
	}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER, 0.1);

	// Subsystems
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain(this.subsystems));

	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = this.nt.getTable("Auto");


	private final AutoSelector autoSelector = new AutoSelector()
			.add("2L", () -> new PathPlannerAuto("2Coral"));

	{
		var profiles = new String[this.autoSelector.getProfiles().size()];
		for (var i = 0; i < profiles.length; i++) {
			profiles[i] = this.autoSelector.getProfiles().get(i).name();
		}

		var autoChoicesPub = NetworkTables.PublisherFactory(this.table, "Choices", profiles);
		autoChoicesPub.accept(profiles);
	}

	private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(this.table, "Profile",
			this.autoSelector.getProfiles().isEmpty() ? "0" : this.autoSelector.getProfiles().get(0).name());

	private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(this.table, this.autoPublisher.getTopic());

		@Override
		public void autonomousSequence () {
			NetworkTables.SetPersistence(this.autoPublisher.getTopic(), true);
			String autoProfile = this.autoSubscriber.get();
			var autoCommand = this.autoSelector.select(autoProfile);

			this.scheduler.scheduleAutoCommand(autoCommand);
		}

		@Override
		public void teleopSequence () {
			this.scheduler.scheduleDefaultCommand(new DriveLooped(
				this.drivetrain,
				this.driverController.LEFT_X_AXIS,
				this.driverController.LEFT_Y_AXIS,
				this.driverController.RIGHT_X_AXIS,
				this.driverController.LEFT_TRIGGER
			), TaskPersistence.EPHEMERAL);

			this.driverController.Y.whenPressed(new InstantCommand(
					() -> {
						if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
							this.drivetrain.setYaw(Rotation2d.fromDegrees(180));
						} else {
							this.drivetrain.setYaw(Rotation2d.fromDegrees(0));
						}
					}
			), TaskPersistence.GAMEPLAY);


			this.driverController.RIGHT_TRIGGER.button().whileHeldOnce(new AutoAlignToReef(
					this.drivetrain
					), TaskPersistence.GAMEPLAY, LockPriority.HIGHEST
			);
	}


		@Override
		public void testSequence () {}

		@Override
		protected void disabledSequence () {}
}
