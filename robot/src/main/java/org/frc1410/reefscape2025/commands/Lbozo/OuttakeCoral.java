package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LBozo;

public class OuttakeCoral extends Command {
    private final LBozo lBozo;

    public OuttakeCoral(LBozo lBozo) {
        this.lBozo = lBozo;
        addRequirements(this.lBozo);
    }

    @Override
    public void initialize() {
        this.lBozo.setLBozoSpeed(-0.4);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
    }
}
