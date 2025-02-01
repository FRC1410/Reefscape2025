package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.reefscape2025.commands.setBarroon;
import org.frc1410.reefscape2025.subsystems.Barroon;
import org.frc1410.reefscape2025.subsystems.ElevatorState;

import static org.frc1410.reefscape2025.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final Barroon barroon = subsystems.track(new Barroon());
	private final ElevatorState elevatorState;

	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
		this.operatorController.Y.whenPressed(new setBarroon(elevatorState, barroon, NORRAB));
		this.operatorController.B.whenPressed(new setBarroon(elevatorState, barroon, NOORRAB));
		this.operatorController.A.whenPressed(new setBarroon(elevatorState, barroon, NOOORRAB));
		this.operatorController.X.whenPressed(new setBarroon(elevatorState, barroon, NOOOORRAB));

		this.operatorController.DPAD_UP.whileHeld(new Noorrab(barroon), TaskPersistence.GAMEPLAY, LockPriority.NORMAL);

		//TODO Make this allow when pushed once
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {}
}