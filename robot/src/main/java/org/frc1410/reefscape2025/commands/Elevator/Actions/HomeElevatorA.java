package org.frc1410.reefscape2025.commands.Elevator.Actions;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static org.frc1410.reefscape2025.util.Constants.HOME_ANGLE;

import org.frc1410.reefscape2025.subsystems.CoralRotation;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.ElevatorStates;

public class HomeElevatorA extends SequentialCommandGroup {
    public HomeElevatorA(Elevator elevator, CoralRotation coralRotation) {
        this.addCommands(
                new InstantCommand(() -> coralRotation.setDesiredLevelAngle(ElevatorStates.HOME)),
                new WaitCommand(0.5),
                new InstantCommand(() -> elevator.setDesiredElevatorState(ElevatorStates.HOME)),
                new WaitCommand(0.7)
        );
    }
}
