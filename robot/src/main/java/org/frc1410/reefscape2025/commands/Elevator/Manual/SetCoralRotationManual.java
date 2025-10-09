package org.frc1410.reefscape2025.commands.Elevator.Manual;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

import edu.wpi.first.wpilibj2.command.Command;

public class SetCoralRotationManual extends Command{
    private final CoralRotation coralRotation;
    private double change;
    private Axis axis;

    public SetCoralRotationManual(CoralRotation coralRotation, Axis axis, double change) {
        this.axis = axis;
        this.coralRotation = coralRotation;
        this.change = change;
    }
    
    @Override
    public void execute() {
        if (this.axis.get() > .1) {
            this.coralRotation.setPositionManual(change);
        }
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if(this.change < 0) {
//            this.coralRotation.resetRotationEncoder();

            this.coralRotation.setDesiredCoralRotation(SuperStructure.HOME);
        } 
    }
}
