package org.frc1410.reefscape2025.commands.Elevator.Manual;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class IntakeAngleManual extends Command {
    private CoralRotation coralRotation;
    private boolean isInverted;

    public IntakeAngleManual(CoralRotation coralRotation, boolean isInverted) {
        this.coralRotation = coralRotation;
        this.isInverted = isInverted;
        addRequirements(coralRotation);
    }

    @Override
    public void initialize() {
        
       

    }

    @Override
    public void execute() {
        if (isInverted) {
            this.coralRotation.setCoralAngleManually(-0.3);
        } else {
            this.coralRotation.setCoralAngleManually(0.3);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if(!this.isInverted) {
            this.coralRotation.resetAngleEncoder();
            this.coralRotation.setCoralAngleManually(0);
        }
        
    }
}
