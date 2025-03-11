package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class IntakeCoral extends Command {
    private final LBozo lBozo;
    private final Elevator elevator;
    private final LEDs leds;

    public IntakeCoral(LBozo lBozo, Elevator elevator, LEDs leds) {
        this.lBozo = lBozo;
        this.elevator = elevator;
        this.leds = leds;
        addRequirements(this.lBozo);
    }

    @Override
    public void initialize() {
        this.leds.setColor(LEDs.Color.ORANGE);
    }

    @Override
    public void execute() {
        this.elevator.setDesiredIntakeState(Elevator.ELEVATOR_STATE.INTAKE);

        this.lBozo.setLBozoSpeed(-0.5);
        this.lBozo.setOuttakeSpeed(0.2);
    }

    @Override
    public boolean isFinished() {
        return this.lBozo.hasCoral();
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
        this.lBozo.setOuttakeSpeed(0);
        this.elevator.resetElevatorEncoder();
        this.leds.setColor(LEDs.Color.LIGHT_BLUE);
    }
}
