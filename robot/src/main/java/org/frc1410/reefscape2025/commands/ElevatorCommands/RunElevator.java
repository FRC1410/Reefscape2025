package org.frc1410.reefscape2025.commands.ElevatorCommands;

import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RunElevator extends SequentialCommandGroup{
    public RunElevator(Elevator elevator) {
        super(
            new IntakePID(elevator),
            new ElevatorPID(elevator)
        );
    }
}
