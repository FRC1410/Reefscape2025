package org.frc1410.reefscape2025.commands.Elevator.Actions;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

import edu.wpi.first.wpilibj2.command.Command;


public class ConfigureLevelSimultanious extends Command{
    private final Elevator elevator;
    private final CoralRotation coralRotation;
    private final SuperStructure structure;


    public ConfigureLevelSimultanious(Elevator elevator, CoralRotation coralRotation, SuperStructure structure) {
        this.elevator = elevator;
        this.coralRotation = coralRotation;
        this.structure = structure;
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredElevatorState(structure);
        this.coralRotation.setDesiredCoralRotation(structure);

    }
   
    @Override
    public boolean isFinished() {
        return true;
    }

}
