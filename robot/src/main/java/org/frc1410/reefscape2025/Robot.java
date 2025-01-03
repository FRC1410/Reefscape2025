package org.frc1410.reefscape2025;
// PLEASE

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	@Override
	public void autonomousSequence() {
	}

	@Override
	public void teleopSequence() {
		//Webhook test :)
	}


	@Override
	public void testSequence() {
	}

	@Override
	protected void disabledSequence() {

	}
}
