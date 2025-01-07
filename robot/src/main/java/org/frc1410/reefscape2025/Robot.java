package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.DriveLooped;
import org.frc1410.reefscape2025.subsystems.Drivetrain;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	// Subsystems
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain(this.subsystems));

	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
		this.scheduler.scheduleDefaultCommand(new DriveLooped(
				this.drivetrain,
				this.driverController.LEFT_Y_AXIS,
				this.driverController.LEFT_X_AXIS,
				this.driverController.RIGHT_X_AXIS,
				this.driverController.LEFT_TRIGGER
		), TaskPersistence.EPHEMERAL);
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {}
}
