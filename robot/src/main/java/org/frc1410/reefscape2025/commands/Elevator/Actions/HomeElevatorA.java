package org.frc1410.reefscape2025.commands.Elevator.Actions;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import org.frc1410.reefscape2025.subsystems.Elevator;

public class HomeElevatorA extends SequentialCommandGroup {
    public HomeElevatorA(Elevator elevator) {
        this.addCommands(
                new InstantCommand(() -> elevator.setDesiredIntakeState(Elevator.ELEVATOR_STATE.HOME)),
                new WaitUntilCommand(elevator::intakeRotationAtSetpoint),
                new InstantCommand(elevator::setDesiredElevatorState),
                new WaitCommand(0)

        );
    }
}
