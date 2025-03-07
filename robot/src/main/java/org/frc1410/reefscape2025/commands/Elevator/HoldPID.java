package org.frc1410.reefscape2025.commands.Elevator;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;

public class HoldPID extends Command{
    private final Elevator elevator;
    private final CoralRotation coralRotation;

    public HoldPID(Elevator elevator, CoralRotation coralRotation) {
        this.elevator = elevator;
        this.coralRotation = coralRotation;
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        
    }
}
