package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.Elevator.*;
import org.frc1410.reefscape2025.commands.Elevator.Actions.ConfigureElevatorHeight;
import org.frc1410.reefscape2025.commands.Elevator.Actions.ConfigureIntakeAngle;
import org.frc1410.reefscape2025.commands.Elevator.Actions.HomeElevator;
import org.frc1410.reefscape2025.commands.Elevator.Manual.ElevatorManual;
import org.frc1410.reefscape2025.commands.Elevator.Manual.IntakeAngleManual;
import org.frc1410.reefscape2025.commands.Lbozo.IntakeCoral;
import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.Climber;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final Elevator elevator = subsystems.track(new Elevator());

	private final LBozo lBozo = subsystems.track(new LBozo());
	private final Climber climber = subsystems.track(new Climber());
	private final LEDs leds = subsystems.track(new LEDs());


	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
//		this.operatorController.X.whileHeld(new IntakeCoral(lBozo, leds), TaskPersistence.GAMEPLAY);
//		this.operatorController.Y.whileHeld(new OuttakeCoral(lBozo, leds), TaskPersistence.GAMEPLAY);

//		 this.scheduler.scheduleDefaultCommand(new ElevatorManual(elevator, this.operatorController.LEFT_Y_AXIS), TaskPersistence.GAMEPLAY);
		// this.scheduler.scheduleDefaultCommand(new IntakeAngleManual(elevator, this.operatorController.RIGHT_Y_AXIS), TaskPersistence.GAMEPLAY);

		this.operatorController.Y.whenPressed(new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.L4), TaskPersistence.GAMEPLAY);
		this.operatorController.B.whenPressed(new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.L3), TaskPersistence.GAMEPLAY);
		this.operatorController.A.whenPressed(new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.L2), TaskPersistence.GAMEPLAY);
		this.operatorController.X.whenPressed(new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.L1), TaskPersistence.GAMEPLAY);

		this.operatorController.DPAD_UP.whenPressed(new ConfigureElevatorHeight(elevator), TaskPersistence.GAMEPLAY);
		this.operatorController.DPAD_DOWN.whenPressed(new HomeElevator(elevator), TaskPersistence.GAMEPLAY);



		this.scheduler.scheduleDefaultCommand(new HoldElevatorPID(elevator), TaskPersistence.GAMEPLAY);
	
	}

	@Override
	public void testSequence() {
		
	}

	@Override
	protected void disabledSequence() {}
}
