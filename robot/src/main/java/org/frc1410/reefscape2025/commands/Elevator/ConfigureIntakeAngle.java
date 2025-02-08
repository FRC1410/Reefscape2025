package org.frc1410.reefscape2025.commands.Elevator;
import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class ConfigureIntakeAngle extends Command{
    private final Elevator elevator;
    private final Elevator.ELEVATOR_STATE desiredElevatorState;

    public ConfigureIntakeAngle(Elevator elevator, Elevator.ELEVATOR_STATE desiredElevatorState) {
        this.elevator = elevator;
        this.desiredElevatorState = desiredElevatorState;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredIntakeState(desiredElevatorState);
    }

    @Override
    public boolean isFinished() {return true;}
}
