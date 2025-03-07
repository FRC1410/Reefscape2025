package org.frc1410.reefscape2025.commands.Elevator.Actions;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.ElevatorStates;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class ConfigureLevel extends SequentialCommandGroup{

    public ConfigureLevel(Elevator elevator, CoralRotation coralRotation, ElevatorStates elevatorStates) {
        if(elevator.getCurrentElevatorDistance() <= elevatorStates.getDesiredElevatorHeight()) {
            this.addCommands(
                new InstantCommand(() -> elevator.setDesiredElevatorState(elevatorStates)),
                new WaitUntilCommand(() -> elevator.atSetpoint()),
                new InstantCommand(() -> coralRotation.setDesiredLevelAngle(elevatorStates))
            );
        } else {
            this.addCommands(
                new InstantCommand(() -> coralRotation.setDesiredLevelAngle(elevatorStates)),
                new WaitUntilCommand(() -> coralRotation.atSetpoint()),
                new InstantCommand(() -> elevator.setDesiredElevatorState(elevatorStates))
            );
        }
    }  
}
