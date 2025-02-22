package org.frc1410.reefscape2025.commands.Elevator.Actions;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.reefscape2025.commands.Lbozo.IntakeCoral;
import org.frc1410.reefscape2025.commands.Lbozo.RunLbozoTimed;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class IntakeAction extends SequentialCommandGroup {
    public IntakeAction(Elevator elevator, LBozo lBozo, LEDs leds) {
        Timer timer = new Timer();
        timer.reset();
        timer.start();

            this.addCommands(
                new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.INTAKE, leds),
                new IntakeCoral(lBozo, leds)
//                new RunLbozoTimed(lBozo, 0.5)
        );
    }
}
