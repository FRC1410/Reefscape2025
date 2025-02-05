package org.frc1410.reefscape2025.commands.Elevator;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class GoToState extends Command {
    private final Elevator elevator;

    public GoToState(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.elevator.goToDesiredHeight();
        this.elevator.goToDesiredAngle();

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setManualSpeed(0);
    }

}