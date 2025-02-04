package org.frc1410.reefscape2025;

import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.Actual;
import org.frc1410.reefscape2025.commands.ChangeAnimation;
import org.frc1410.reefscape2025.commands.ChangeLEDColor;
import org.frc1410.reefscape2025.subsystems.LEDs;
import org.frc1410.reefscape2025.commands.Lbozo.IntakeCoral;
import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.Climber;
import org.frc1410.reefscape2025.subsystems.LBozo;

import static org.frc1410.reefscape2025.util.IDs.*;

public final class Robot extends PhaseDrivenRobot {
	public Robot() {
	}

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER,  0.1);

	private final LBozo lBozo = subsystems.track(new LBozo());
	private final Climber climber = subsystems.track(new Climber());
  private final LEDs leds = new LEDs();


	@Override
	public void autonomousSequence() {}

	@Override
	public void teleopSequence() {
    // this needs to be updated with the correct control scheme
		this.operatorController.X.whileHeld(new IntakeCoral(lBozo), TaskPersistence.GAMEPLAY);
		this.operatorController.Y.whileHeld(new OuttakeCoral(lBozo), TaskPersistence.GAMEPLAY);
	}


	@Override
	public void testSequence() {}

	@Override
	protected void disabledSequence() {

	}
}
