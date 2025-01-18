package org.frc1410.reefscape2025;

import com.pathplanner.lib.auto.NamedCommands;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.Commands.SuperSeniorLooped;
import org.frc1410.reefscape2025.subsystems.SuperSenior;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {
		NamedCommands.registerCommand("runSuperSenior", new SuperSeniorLooped(superSenior, driverController.LEFT_X_AXIS, driverController.RIGHT_X_AXIS));
	}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final SuperSenior superSenior = subsystems.track(new SuperSenior());

	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
		this.scheduler.scheduleDefaultCommand(new SuperSeniorLooped(superSenior, driverController.LEFT_X_AXIS, driverController.RIGHT_X_AXIS), TaskPersistence.EPHEMERAL);
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {}
}
