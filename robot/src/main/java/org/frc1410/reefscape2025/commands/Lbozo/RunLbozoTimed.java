package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.frc1410.reefscape2025.subsystems.LBozo;

public class RunLbozoTimed extends Command {
    private final LBozo lBozo;
    private final double time;

    private final Timer timer = new Timer();

    public RunLbozoTimed(LBozo lBozo, double time) {
        this.lBozo = lBozo;
        this.time = time;
    }

    @Override
    public void initialize() {
        this.timer.reset();
        this.timer.start();
    }

    @Override
    public void execute() {
        if(timer.hasElapsed(this.time)) {
            this.lBozo.setLBozoSpeed(0);
        } else {
            this.lBozo.setLBozoSpeed(0.5);
        }
    }
}
