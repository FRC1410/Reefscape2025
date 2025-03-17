package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;

import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class OuttakeCoral extends Command {
    private final LBozo lBozo;
    private final LEDs leds;
    private boolean forward;

    public OuttakeCoral(LBozo lBozo, LEDs leds, boolean forward) {
        this.lBozo = lBozo;
        this.leds = leds;
        this.forward = forward;
        addRequirements(this.lBozo);
    }

    @Override
    public void initialize() {
        if(this.forward) {
            this.lBozo.setLBozoSpeed(0.4);
            this.lBozo.setOuttakeSpeed(0.4);
        } else {
            this.lBozo.setOuttakeSpeed(-0.2);
        }
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
        this.lBozo.setOuttakeSpeed(0);
    }
}
