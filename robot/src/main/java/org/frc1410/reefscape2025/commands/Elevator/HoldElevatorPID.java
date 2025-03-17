package org.frc1410.reefscape2025.commands.Elevator;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

import edu.wpi.first.wpilibj2.command.Command;

public class HoldElevatorPID extends Command{
    private final Elevator elevator;
    private final CoralRotation coralRotation;

    public HoldElevatorPID(Elevator elevator, CoralRotation coralRotation) {
        this.elevator = elevator;
        this.coralRotation = coralRotation;
    }

    @Override
    public void initialize() {
        this.coralRotation.setDesiredCoralRotation(SuperStructure.HOME);
        this.elevator.setDesiredElevatorState(SuperStructure.HOME);
    }
    
    @Override
    public void execute() {
       this.elevator.goToDesiredHeight();
       this.coralRotation.goToAngle();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}