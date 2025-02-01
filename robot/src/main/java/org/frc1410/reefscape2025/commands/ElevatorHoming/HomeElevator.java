package org.frc1410.reefscape2025.commands.ElevatorHoming;

import org.frc1410.reefscape2025.commands.ElevatorCommands.ConfigureHeight;
import org.frc1410.reefscape2025.commands.ElevatorCommands.ElevatorPID;
import org.frc1410.reefscape2025.commands.ElevatorCommands.IntakePID;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.ElevatorState;
import static org.frc1410.reefscape2025.util.Constants.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class HomeElevator extends SequentialCommandGroup{
    public HomeElevator(Elevator elevator, ElevatorState elevatorState) {
        super(
            new ConfigureHeight(elevatorState, elevator, HOME, HOME),
            new IntakePID(elevator),
            new ElevatorPID(elevator)
        );
    }
    
}
