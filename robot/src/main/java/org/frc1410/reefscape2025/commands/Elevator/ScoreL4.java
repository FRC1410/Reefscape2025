package org.frc1410.reefscape2025.commands.Elevator;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import org.frc1410.reefscape2025.commands.Elevator.Actions.ConfigureElevatorHeight;
import org.frc1410.reefscape2025.commands.Elevator.Actions.ConfigureIntakeAngle;
import org.frc1410.reefscape2025.commands.Elevator.Actions.HomeElevatorA;
import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class ScoreL4 extends SequentialCommandGroup {
    public ScoreL4(Elevator elevator, LBozo lBozo, LEDs leds) {
        this.addCommands(
                new ParallelRaceGroup(
                        new HoldElevatorPID(elevator),
                        new SequentialCommandGroup(
                                new ConfigureIntakeAngle(elevator, Elevator.ELEVATOR_STATE.L4, leds),
                                new ConfigureElevatorHeight(elevator),
                                new WaitUntilCommand(elevator::elevatorHeightAtSetpoint),
                                new ParallelRaceGroup(
                                        new OuttakeCoral(lBozo, leds, elevator),
                                        new WaitCommand(0.5)
                                ),
                                new HomeElevatorA(elevator),
                                new WaitCommand(1)
                        )
                )
        );
    }
}
