package org.frc1410.reefscape2025.commands.Elevator.Actions;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class HomeElevator extends Command {

    private final Elevator elevator;

    public HomeElevator(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredIntakeState(Elevator.ELEVATOR_STATE.HOME);
        this.elevator.setDesiredElevatorState();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
