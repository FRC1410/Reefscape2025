package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.ConfigureHeight;
import org.frc1410.reefscape2025.commands.ElevatorManual;
import org.frc1410.reefscape2025.commands.GoToState;
import org.frc1410.reefscape2025.subsystems.Elevator;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final Elevator elevator = subsystems.track(new Elevator());

	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
		//Operator
		this.scheduler.scheduleDefaultCommand(new ElevatorManual(elevator, this.operatorController.LEFT_Y_AXIS), TaskPersistence.GAMEPLAY);

		this.operatorController.Y.whenPressed(new ConfigureHeight(elevator, Elevator.ELEVATOR_STATE.L4), TaskPersistence.GAMEPLAY);
		this.operatorController.B.whenPressed(new ConfigureHeight(elevator, Elevator.ELEVATOR_STATE.L3), TaskPersistence.GAMEPLAY);
		this.operatorController.A.whenPressed(new ConfigureHeight(elevator, Elevator.ELEVATOR_STATE.L2), TaskPersistence.GAMEPLAY);
		this.operatorController.X.whenPressed(new ConfigureHeight(elevator, Elevator.ELEVATOR_STATE.L1), TaskPersistence.GAMEPLAY);
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {}
}