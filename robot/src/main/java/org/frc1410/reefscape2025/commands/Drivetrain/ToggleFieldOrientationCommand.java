package org.frc1410.reefscape2025.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Drivetrain;

public class ToggleFieldOrientationCommand extends Command {
    private final Drivetrain drivetrain;


    public ToggleFieldOrientationCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        this.drivetrain.switchOrientation();
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
