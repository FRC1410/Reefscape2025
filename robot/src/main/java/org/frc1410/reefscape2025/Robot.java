package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.ChangeAnimation;
import org.frc1410.reefscape2025.commands.ChangeLEDColor;
import org.frc1410.reefscape2025.subsystems.LEDs;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final LEDs leds = new LEDs();

	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
		driverController.DPAD_UP_LEFT.whenPressed(new ChangeAnimation(leds), TaskPersistence.GAMEPLAY);
		driverController.Y.whenPressed(new ChangeLEDColor(leds), TaskPersistence.GAMEPLAY);
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {
		leds.setAnimation(LEDs.Animation.RAINBOW, LEDs.Color.NULL, 0.5);
	}
}
