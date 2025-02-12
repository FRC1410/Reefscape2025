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
        this.elevator.resetElevatorEncoder();
        this.elevator.resetIntakeRotationEncoder();
    }
    
    @Override
    public void execute() {
//        if(this.elevator.getDesiredElevatorState() != 10) { //Shitty code to tell it not to immediatly go to position, need to make safer
//            this.elevator.goToDesiredHeight();
//            this.elevator.goToDesiredAngle(); // uncomment when intake rotation mech is working again
//        } else {
//            this.elevator.setElevatorVolatgeToZero();
//            this.elevator.setIntakeRotationVolatgeToZero();
//        }

            this.elevator.goToDesiredHeight();
            this.elevator.goToDesiredAngle(); // uncomment when intake rotation mech is working again
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}