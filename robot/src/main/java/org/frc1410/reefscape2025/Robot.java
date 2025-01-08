package org.frc1410.reefscape2025;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.DriveLooped;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.util.NetworkTables;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = this.nt.getTable("Auto");

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER, 0.1);

	// Subsystems
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain(this.subsystems));

	private final AutoSelector autoSelector = new AutoSelector();

	{
		var profiles = new String[this.autoSelector.getProfiles().size()];
		for (var i = 0; i < profiles.length; i++) {
			profiles[i] = this.autoSelector.getProfiles().get(i).name();
		}

		var autoChoicesPub = NetworkTables.PublisherFactory(this.table, "Choices", profiles);
		autoChoicesPub.accept(profiles);
	}

	public Robot() {

	}

		@Override
		public void autonomousSequence () {}

		@Override
		public void teleopSequence () {
		this.scheduler.scheduleDefaultCommand(new DriveLooped(
				this.drivetrain,
				this.driverController.LEFT_Y_AXIS,
				this.driverController.LEFT_X_AXIS,
				this.driverController.RIGHT_X_AXIS,
				this.driverController.LEFT_TRIGGER
		), TaskPersistence.EPHEMERAL);
	}


		@Override
		public void testSequence () {}

		@Override
		protected void disabledSequence () {}
}
