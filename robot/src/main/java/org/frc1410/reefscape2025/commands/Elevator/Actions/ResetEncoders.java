package org.frc1410.reefscape2025.commands.Elevator.Actions;

import org.frc1410.reefscape2025.commands.Elevator.Manual.IntakeAngleManual;
import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class ResetEncoders extends Command{
    private final Elevator elevator;
    private boolean resetElevatorEncoder;
    private boolean resetIntakeRotationEncoder;

    public ResetEncoders(Elevator elevator) {
        this.elevator = elevator;
        this.resetElevatorEncoder = resetElevatorEncoder;
     
    }
    
    @Override
    public void initialize() {
        this.elevator.resetElevatorEncoder();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
