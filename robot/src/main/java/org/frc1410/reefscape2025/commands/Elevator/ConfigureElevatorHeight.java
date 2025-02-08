package org.frc1410.reefscape2025.commands.Elevator;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class ConfigureElevatorHeight extends Command {
    private final Elevator elevator;

    public ConfigureElevatorHeight(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredElevatorState();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}