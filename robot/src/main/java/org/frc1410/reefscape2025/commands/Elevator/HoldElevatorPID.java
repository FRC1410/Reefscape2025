package org.frc1410.reefscape2025.commands.Elevator;

import org.frc1410.reefscape2025.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.Command;

public class HoldElevatorPID extends Command{
    private final Elevator elevator;

    public HoldElevatorPID(Elevator elevator) {
        this.elevator = elevator;
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
}