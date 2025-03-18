package org.frc1410.reefscape2025.commands.AutonomousCommands;

import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoSafeOuttake extends Command{
    private final LBozo lBozo;
    private final LEDs leds;


    public AutoSafeOuttake(LBozo lBozo, LEDs leds) {
        this.lBozo = lBozo;
        this.leds = leds;
    }

    @Override
    public void initialize() {
        if(this.lBozo.hasCoral()) {
            this.lBozo.setOuttakeSpeed(-0.6);
        }
    }

    @Override
    public boolean isFinished() {
        return !this.lBozo.hasCoral();
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setOuttakeSpeed(0);
    }



}
