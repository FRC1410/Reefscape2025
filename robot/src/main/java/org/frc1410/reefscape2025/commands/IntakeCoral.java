package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LBozo;


public class IntakeCoral extends Command {
    private final LBozo lBozo;

    public IntakeCoral(LBozo lBozo) {
        this.lBozo = lBozo;
        addRequirements(this.lBozo);
    }

    @Override
    public void initialize() {
        this.lBozo.setLBozoSpeed(0.2);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        if(this.lBozo.getCoral()) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
    }
}
