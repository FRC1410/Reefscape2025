package org.frc1410.reefscape2025.commands;

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
        this.elevator.setDesiredElevatorState(Elevator.ELEVATOR_STATE.HOME);
    }

    @Override
    public void execute() {
        this.elevator.goToDesiredHeight();
        this.elevator.goToDesiredAngle();
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setManualSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
