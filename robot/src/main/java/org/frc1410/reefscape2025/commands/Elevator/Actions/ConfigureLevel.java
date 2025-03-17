package org.frc1410.reefscape2025.commands.Elevator.Actions;

import javax.swing.GroupLayout.SequentialGroup;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class ConfigureLevel extends SequentialCommandGroup {
    public ConfigureLevel(Elevator elevator, CoralRotation coralRotation, SuperStructure superStructure) {
        if(elevator.getDesiredElevatorState() < elevator.getCurrentElevatorDistance()) {
            this.addCommands(
                new InstantCommand(() -> coralRotation.setDesiredCoralRotation(superStructure)),
                new WaitUntilCommand(coralRotation::coralRotationAtSetpoint),
                new InstantCommand(() -> elevator.setDesiredElevatorState(superStructure))
            );
        } else {
            this.addCommands(
                new InstantCommand(() -> elevator.goToDesiredHeight()),
                new WaitUntilCommand(elevator::elevatorHeightAtSetpoint),
                new InstantCommand(() -> coralRotation.setDesiredCoralRotation(superStructure))
            );
        }
    }
}
