package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LEDs;


public class SetColorCommand extends Command {

    private final LEDs leds;

    public SetColorCommand(LEDs leds) {
        this.leds = leds;

        addRequirements(leds);
    }


    @Override
    public void initialize() {
        this.leds.setColor(LEDs.Color.OCEAN_BREEZE);
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

    }
}
