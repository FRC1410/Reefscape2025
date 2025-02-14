package org.frc1410.reefscape2025.commands.Elevator;

import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.Elevator.ELEVATOR_STATE;

import edu.wpi.first.wpilibj2.command.Command;

public class HoldElevatorPID extends Command{
    private final Elevator elevator;

    public HoldElevatorPID(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredIntakeState(ELEVATOR_STATE.Saftey);
        this.elevator.setDesiredElevatorState();
        
        this.elevator.resetElevatorEncoder();
        this.elevator.resetIntakeRotationEncoder();
    }
    
    @Override
    public void execute() {
       if(this.elevator.getDesiredElevatorState() != 1) { //Shitty code to tell it not to immediatly go to position, need to make safer
           if(this.elevator.getDesiredElevatorState() != 0 || this.elevator.getCurrentElevatorDistance() > 250) {
               this.elevator.goToDesiredHeight();
        } else {
               this.elevator.setElevatorVolatgeToZero();
           }
       } else {
           this.elevator.setElevatorVolatgeToZero();
       }
//       this.elevator.goToDesiredAngle();// uncomment when intake rotation mech is working again
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}