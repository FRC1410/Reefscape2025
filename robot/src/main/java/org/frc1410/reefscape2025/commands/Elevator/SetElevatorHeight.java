package org.frc1410.reefscape2025.commands.Elevator;

import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class SetElevatorHeight extends Command{
    private final Elevator elevator;

    public SetElevatorHeight(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void initialize() {
       
    }
    
    @Override
    public void execute() {
        this.elevator.goToDesiredHeight();
        // TODO: LED code in here
    }

    @Override
    public boolean isFinished() {
        return this.elevator.elevatorHeightAtSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setElevatorVolatgeToZero();
    }
    
}
