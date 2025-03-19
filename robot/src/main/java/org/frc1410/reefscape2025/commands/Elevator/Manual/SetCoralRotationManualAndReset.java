package org.frc1410.reefscape2025.commands.Elevator.Manual;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

public class SetCoralRotationManualAndReset extends Command{
    private final CoralRotation coralRotation;
    private double change;



    public SetCoralRotationManualAndReset(CoralRotation coralRotation, double change) {
        this.coralRotation = coralRotation;
        this.change = change;
    }
    
    @Override
    public void execute() {
        this.coralRotation.setPositionManual(change);
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if(this.change < 0) {
            this.coralRotation.resetRotationEncoder();
            this.coralRotation.setDesiredCoralRotation(SuperStructure.HOME);
        } 
    }
}
