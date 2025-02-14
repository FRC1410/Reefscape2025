package org.frc1410.reefscape2025.commands.Elevator.Actions;
import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LEDs;

import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.*;
import static org.frc1410.reefscape2025.subsystems.LEDs.Animation.FADE_IN_OUT;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.*;
import static org.frc1410.reefscape2025.subsystems.LEDs.Color.GREEN;

public class ConfigureIntakeAngle extends Command{
    private final Elevator elevator;
    private final Elevator.ELEVATOR_STATE desiredElevatorState;
    private final LEDs leds;

    public ConfigureIntakeAngle(Elevator elevator, Elevator.ELEVATOR_STATE desiredElevatorState, LEDs leds) {
        this.elevator = elevator;
        this.desiredElevatorState = desiredElevatorState;
        this.leds = leds;
        addRequirements(elevator, leds);
    }

    @Override
    public void initialize() {
//        this.elevator.setDesiredIntakeState(desiredElevatorState);

        switch (desiredElevatorState) {
            case HOME -> this.leds.setAnimation(RAINBOW, NULL, 0.3);
            case L1 -> this.leds.setAnimation(FADE_IN_OUT, RED, 0.2);
            case L2 -> this.leds.setAnimation(FADE_IN_OUT, PURPLE, 0.5);
            case L3 -> this.leds.setAnimation(FADE_IN_OUT, BLUE, 0.7);
            case L4 -> this.leds.setAnimation(FADE_IN_OUT, GREEN, 0.9);
        }
    }

    @Override
    public boolean isFinished() {return true;}
}
