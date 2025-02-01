package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.reefscape2025.commands.ElevatorCommands.ConfigureHeight;
import org.frc1410.reefscape2025.commands.ElevatorCommands.ElevatorPID;
import org.frc1410.reefscape2025.commands.ElevatorCommands.RunElevator;
import org.frc1410.reefscape2025.commands.ElevatorHoming.HomeElevator;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.ElevatorState;

import static org.frc1410.reefscape2025.util.Constants.*;
import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final Elevator elevator = subsystems.track(new Elevator());
	private final ElevatorState elevatorState = subsystems.track(new ElevatorState());

	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
		this.operatorController.Y.whenPressed(new ConfigureHeight(elevatorState, elevator, L1, L1L3), TaskPersistence.GAMEPLAY);
		this.operatorController.B.whenPressed(new ConfigureHeight(elevatorState, elevator, L2, L1L3), TaskPersistence.GAMEPLAY);
		this.operatorController.A.whenPressed(new ConfigureHeight(elevatorState, elevator, L3, L1L3), TaskPersistence.GAMEPLAY);
		this.operatorController.X.whenPressed(new ConfigureHeight(elevatorState, elevator, L4, L4), TaskPersistence.GAMEPLAY);

		this.operatorController.LEFT_BUMPER.whenPressed(new ConfigureHeight(elevatorState, elevator, INTAKE, HOME), TaskPersistence.GAMEPLAY);
		
		this.operatorController.DPAD_UP.whenPressed(new RunElevator(elevator), TaskPersistence.EPHEMERAL);
		this.operatorController.DPAD_DOWN.whenPressed(new HomeElevator(elevator, elevatorState), TaskPersistence.EPHEMERAL);
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {}
}