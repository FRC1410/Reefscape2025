package org.frc1410.reefscape2025.commands;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class ConfigureHeight extends Command {
    private final Elevator elevator;
    private final Elevator.ELEVATOR_STATE desiredElevatorState;

    public ConfigureHeight(Elevator elevator, Elevator.ELEVATOR_STATE desiredElevatorState) {
        this.elevator = elevator;
        this.desiredElevatorState = desiredElevatorState;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredElevatorState(desiredElevatorState);
    }
    
    @Override
    public void execute() {
        // TODO: LED code in here
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {}

}