package org.frc1410.reefscape2025.commands.Elevator.Actions;

import org.frc1410.reefscape2025.commands.Elevator.Manual.IntakeAngleManual;
import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.ElevatorStates;

import edu.wpi.first.wpilibj2.command.Command;

public class Test extends Command {
    private final CoralRotation coralRotation;

    public Test(CoralRotation coralRotation) {
        this.coralRotation = coralRotation;
    }
    
    @Override
    public void initialize() {
        coralRotation.setDesiredLevelAngle(ElevatorStates.INTAKE);
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
