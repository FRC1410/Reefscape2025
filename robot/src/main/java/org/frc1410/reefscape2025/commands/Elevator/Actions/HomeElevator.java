package org.frc1410.reefscape2025.commands.Elevator.Actions;

import javax.swing.GroupLayout.SequentialGroup;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class HomeElevator extends SequentialCommandGroup {
    public HomeElevator(Elevator elevator, CoralRotation coralRotation, SuperStructure superStructure) {
        this.addCommands(
            new InstantCommand(() -> coralRotation.setDesiredCoralRotation(SuperStructure.HOME)),
            new WaitCommand(0.8),
            new InstantCommand(() -> elevator.setDesiredElevatorState(SuperStructure.HOME))
        );
        
    }
}
