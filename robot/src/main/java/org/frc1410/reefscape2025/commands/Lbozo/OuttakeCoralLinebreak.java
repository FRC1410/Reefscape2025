package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;

import java.util.Timer;

import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class OuttakeCoralLinebreak extends Command {
    private final LBozo lBozo;
    private final LEDs leds;

    private boolean end;

    edu.wpi.first.wpilibj.Timer timer = new edu.wpi.first.wpilibj.Timer();

    public OuttakeCoralLinebreak(LBozo lBozo, LEDs leds) {
        this.lBozo = lBozo;
        this.leds = leds;
        addRequirements(this.lBozo);
    }
    

    @Override
    public void initialize() {
        timer.reset();
        this.lBozo.setLBozoSpeed(-0.15);
    }

    @Override
    public void execute() {
        timer.start();

    }

    @Override
    public boolean isFinished() {
        if(timer.get() > 0.15) {
            return true;
        } else {
            return false;
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
        timer.reset();
    }
}
