package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class IntakeCoral extends Command {
    private final LBozo lBozo;
    private final LEDs leds;
    private int direction;


    public IntakeCoral(LBozo lBozo, LEDs leds, int direction) {
        this.lBozo = lBozo;
        this.leds = leds;
        this.direction = direction;

        addRequirements(this.lBozo);
    }

    @Override
    public void initialize() {
        this.leds.setColor(LEDs.Color.ORANGE);
    }

    @Override
    public void execute() {
        this.lBozo.setLBozoSpeed(-0.5 * direction);
        this.lBozo.outtake(0.4 * direction);
    }

    @Override
    public boolean isFinished() {
        return this.lBozo.hasCoral();
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
        this.lBozo.outtake(0);
        this.leds.setColor(LEDs.Color.LIGHT_BLUE);
    }
}
