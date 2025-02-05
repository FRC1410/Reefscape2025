package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class ElevatorManual extends Command {
    private Elevator elevator;
    private Axis axis;

    public ElevatorManual(Elevator elevator, Axis axis) {
        this.elevator = elevator;
        this.axis = axis;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double speed = axis.getRaw();
        this.elevator.setManualSpeed(speed);
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
