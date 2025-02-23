package org.frc1410.reefscape2025.commands.Elevator.Actions;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class AutoScore extends SequentialCommandGroup {
    public AutoScore(Elevator elevator, LBozo lBozo, Elevator.ELEVATOR_STATE elevatorState, LEDs leds) {
        this.addCommands(
                new InstantCommand(() -> elevator.setDesiredIntakeStateAuto(elevatorState)),
                new InstantCommand(elevator::setDesiredElevatorState),
                new InstantCommand(elevator::goToDesiredHeight),
                new WaitCommand(1),
                new InstantCommand(() -> elevator.setIntakeAngleMotorSetpoint(elevatorState)),
                new WaitCommand(0.7)
        );
    }
}
