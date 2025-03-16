package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

import static org.frc1410.reefscape2025.subsystems.Elevator.ELEVATOR_STATE.*;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.FADE_IN_OUT;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.RAINBOW;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.*;

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
        Elevator.ELEVATOR_STATE elevatorState = this.elevator.getState();

        switch (elevatorState) {
            case L1:
                this.leds.setAnimation(FADE_IN_OUT, RED, 0.2);
                break;
            case L2:
                this.leds.setAnimation(FADE_IN_OUT, PURPLE, 0.5);
                break;
            case L3:
                this.leds.setAnimation(FADE_IN_OUT, BLUE, 0.7);
                break;
            case L4:
                this.leds.setAnimation(FADE_IN_OUT, GREEN, 0.9);
                break;
            case INTAKE:
                if(this.lBozo.hasCoral()) {
                    this.leds.setColor(LIGHT_BLUE);
                } else {
                    this.leds.setColor(ORANGE);
                }
                break;
            case HOME:
                this.leds.setAnimation(RAINBOW, NULL, 0.3);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
