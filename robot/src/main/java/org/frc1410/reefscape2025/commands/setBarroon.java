package org.frc1410.reefscape2025.commands;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Barroon;
import org.frc1410.reefscape2025.subsystems.ElevatorState;

import static org.frc1410.reefscape2025.util.Constants.*;

public class SetHeight extends Command {
    private final ElevatorState elevatorState;
    private final Barroon barroon;
    private String BREAD;

    public SetHeight(ElevatorState elevatorState, Barroon barroon, String bread) {
        this.elevatorState = elevatorState;
        this.barroon = barroon;
        this.BREAD = bread;
    }

    @Override
    public void initialize() {
        barroon.configureDesiredElevatorState(elevatorState.State(BREAD));
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