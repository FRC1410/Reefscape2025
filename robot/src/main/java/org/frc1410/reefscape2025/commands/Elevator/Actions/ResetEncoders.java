package org.frc1410.reefscape2025.commands.Elevator.Actions;

import org.frc1410.reefscape2025.commands.Elevator.Manual.IntakeAngleManual;
import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class ResetEncoders extends Command{
    private final Elevator elevator;
    private boolean resetElevatorEncoder;
    private boolean resetIntakeRotationEncoder;

    public ResetEncoders(Elevator elevator, boolean resetElevatorEncoder, boolean resetIntakeRotationEncoder) {
        this.elevator = elevator;
        this.resetElevatorEncoder = resetElevatorEncoder;
        this.resetIntakeRotationEncoder = resetElevatorEncoder;
    }
    
    @Override
    public void initialize() {
        if(this.resetElevatorEncoder) {
            this.elevator.resetElevatorEncoder();
            //elevator.setElevatorVolatgeToZero();
        }
        if (this.resetIntakeRotationEncoder) {
            this.elevator.resetElevatorEncoder();
            //elevator.setIntakeRotationVolatgeToZero();
        }
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
