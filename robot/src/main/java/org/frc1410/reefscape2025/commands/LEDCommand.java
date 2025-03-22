package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;
import org.frc1410.reefscape2025.subsystems.SuperStructure;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.FADE_IN_OUT;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.RAINBOW;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.*;
import static org.frc1410.reefscape2025.util.Constants.*;


public class LEDCommand extends Command {
    private final LEDs leds;
    private final Elevator elevator;
    private final LBozo lBozo;


    public LEDCommand(LEDs leds, Elevator elevator, LBozo lBozo) {
        this.leds = leds;
        this.elevator = elevator;
        this.lBozo = lBozo;
    }

    @Override
    public void execute() {
        int state = elevator.getDesiredElevatorDistance();          

        switch (state) {
            case 2:
                this.leds.setAnimation(FADE_IN_OUT, RED, 0.2);
                break;
            case 3600 + 250:
                this.leds.setAnimation(FADE_IN_OUT, PURPLE, 0.5);
                break;
            case 6218 + 250:
                this.leds.setAnimation(FADE_IN_OUT, BLUE, 0.7);
                break;
            case 11280 + 175:
                this.leds.setAnimation(FADE_IN_OUT, GREEN, 0.9);
                break;
            case 1:
                if(this.lBozo.hasCoral()) {
                    this.leds.setColor(LIGHT_BLUE);
                } else {
                    this.leds.setColor(ORANGE);
                }
                break;
            default:
                this.leds.setAnimation(RAINBOW, NULL, 0.3);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
