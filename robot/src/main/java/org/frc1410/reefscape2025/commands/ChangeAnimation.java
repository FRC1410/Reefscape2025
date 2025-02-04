package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LEDs;

import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.*;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.*;

public class ChangeAnimation extends Command {

    private final LEDs leds;
    private int animationCounter = 0;

    private final LEDs.Color color = TEAL;
    private final double speed = 0.6;

    public ChangeAnimation(LEDs leds) {
        this.leds = leds;
    }

    @Override
    public void initialize() {
        if(this.animationCounter >= 9) {
            this.animationCounter = 1;
        } else {
            this.animationCounter++;
            System.out.println(animationCounter);
        }

        switch (this.animationCounter) {
            case 1 -> this.leds.setAnimation(RAINBOW, NULL, this.speed);
            case 2 -> this.leds.setAnimation(BOUNCE, this.color, this.speed);
            case 3 -> this.leds.setAnimation(STROBE, this.color, this.speed);
            case 4 -> this.leds.setAnimation(GRADUAL_FILL, this.color, this.speed);
            case 5 -> this.leds.setAnimation(FADE_IN_OUT, this.color, this.speed);
            case 6 -> this.leds.setAnimation(TWINKLE, this.color, this.speed);
            case 7 -> this.leds.setAnimation(FIRE, this.color, this.speed);
            case 8 -> this.leds.setAnimation(RGB_FADE, this.color, this.speed);
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
