package org.frc1410.reefscape2025.commands.AutonomousCommands;

import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoSafeOuttake extends ParallelRaceGroup{
    public AutoSafeOuttake(LBozo lBozo, LEDs leds) {
        if(lBozo.hasCoral()) {
            this.addCommands(
                new OuttakeCoral(lBozo, leds, false),
                new WaitCommand(0.5)
            );
        } 
    }
}
