package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LEDs;

import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.*;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.*;

public class Actual extends Command {

    private final LEDs leds;
    private int animationCounter = 0;

    private final double speed = 0.6;

    public Actual(LEDs leds) {
        this.leds = leds;
    }

    @Override
    public void initialize() {
        if(this.animationCounter >= 8) {
            this.animationCounter = 1;
        } else {
            this.animationCounter++;
            System.out.println(animationCounter);
        }

        switch (this.animationCounter) {
            case 1 -> this.leds.setAnimation(RAINBOW, NULL, 0.3);
            case 2 -> this.leds.setColor(ORANGE);
            case 3 -> this.leds.setColor(LIGHT_BLUE);
            case 4 -> this.leds.setAnimation(FIRE, NULL, 0.8);

            case 5 -> this.leds.setAnimation(FADE_IN_OUT, RED, 0.2);
            case 6 -> this.leds.setAnimation(FADE_IN_OUT, PURPLE, 0.5);
            case 7 -> this.leds.setAnimation(FADE_IN_OUT, BLUE, 0.7);
            case 8 -> this.leds.setAnimation(FADE_IN_OUT, GREEN, 0.9);
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
