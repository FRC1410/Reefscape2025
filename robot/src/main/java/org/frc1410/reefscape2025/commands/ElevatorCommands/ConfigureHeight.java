package org.frc1410.reefscape2025.commands.ElevatorCommands;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.ElevatorState;
import static org.frc1410.reefscape2025.util.Constants.*;

public class ConfigureHeight extends Command {
    private final ElevatorState elevatorState;
    private final Elevator elevator;
    private String desiredHeight = L1;
    private String desiredRotation;

    public ConfigureHeight(ElevatorState elevatorState, Elevator elevator, String deiredHeight, String desiredRotation) {
        this.elevatorState = elevatorState;
        this.elevator = elevator;
        this.desiredHeight = deiredHeight;
        this.desiredRotation = desiredRotation;
    }

    @Override
    public void initialize() {
        this.elevator.configureDesiredElevatorState(elevatorState.State(this.desiredHeight));
        this.elevator.configureDesiredIntakeRotation(elevatorState.Rotate(this.desiredRotation));
    }
    
    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {}

}