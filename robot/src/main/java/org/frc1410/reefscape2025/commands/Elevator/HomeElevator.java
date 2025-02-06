package org.frc1410.reefscape2025.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.frc1410.reefscape2025.subsystems.Elevator;

public class HomeElevator extends SequentialCommandGroup {
    public HomeElevator(Elevator elevator) {
        super(
            new ConfigureHeight(elevator, Elevator.ELEVATOR_STATE.HOME),
            new SetElevatorHeight(elevator)
        );
    }
}
