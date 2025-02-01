package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class ChangeLEDColor extends Command {
    private final LEDs leds;

    private int colorCounter = 0;

    public ChangeLEDColor(LEDs leds) {
        this.leds = leds;
    }

    @Override
    public void initialize() {
        if(colorCounter >= 9) {
            colorCounter = 0;
        } else {
            colorCounter++;
        }

        switch (colorCounter) {
            case 1 -> leds.setColor(LEDs.Color.BLUE);
            case 2 -> leds.setColor(LEDs.Color.RED);
            case 3 -> leds.setColor(LEDs.Color.YELLOW);
            case 4 -> leds.setColor(LEDs.Color.TEAL);
            case 5 -> leds.setColor(LEDs.Color.PURPLE);
            case 6 -> leds.setColor(LEDs.Color.LIGHT_BLUE);
            case 7 -> leds.setColor(LEDs.Color.GREEN);
            case 8 -> leds.setColor(LEDs.Color.ORANGE);
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

}
