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
        this.elevator.resetEncoders();
    }
    
    @Override
    public void execute() {
        if(this.elevator.getDesiredElevatorState() != 1) {
            //this.elevator.goToDesiredHeight();
            //this.elevator.goToDesiredAngle();
        } else {
            this.elevator.setElevatorVolatgeToZero();
            this.elevator.setIntakeRotationVolatgeToZero();
        }
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}