package org.frc1410.reefscape2025.commands.Elevator.Actions;
import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class ResetEncoders extends Command{
    private final Elevator elevator;


    public ResetEncoders(Elevator elevator) {
        this.elevator = elevator;
    }
    
    @Override
    public void initialize() {
//        System.out.println(this.elevator.getCurrentElevatorDistance());
        this.elevator.resetElevatorEncoder();
//        System.out.println("Reset Encoders");
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
