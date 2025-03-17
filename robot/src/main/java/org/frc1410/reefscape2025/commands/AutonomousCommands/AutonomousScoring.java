package org.frc1410.reefscape2025.commands.AutonomousCommands;

import java.lang.annotation.ElementType;
import java.time.Instant;

import org.frc1410.reefscape2025.commands.Elevator.HoldElevatorPID;
import org.frc1410.reefscape2025.commands.Elevator.Actions.ConfigureLevelSimultanious;
import org.frc1410.reefscape2025.commands.Lbozo.OuttakeCoral;
import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class AutonomousScoring extends SequentialCommandGroup{
    public AutonomousScoring(Elevator elevator, CoralRotation coralRotation, LBozo lBozo, LEDs leds, SuperStructure level) {
        this.addCommands(
            new ParallelCommandGroup(
                new HoldElevatorPID(elevator, coralRotation),

                new SequentialCommandGroup(

                    new ConfigureLevelSimultanious(elevator, coralRotation, level),
                    new WaitUntilCommand(elevator::elevatorHeightAtSetpoint),

                    new ParallelRaceGroup(
                        new OuttakeCoral(lBozo, leds, true),
                        new WaitCommand(0.5)
                    ),

                    new AutoSafeOuttake(lBozo, leds),

                    new ConfigureLevelSimultanious(elevator, coralRotation, SuperStructure.HOME),

                    new ParallelRaceGroup(
                        new WaitUntilCommand(elevator::elevatorHeightAtSetpoint),
                        new WaitCommand(1.6)
                    )
                    
                )
            )
        );
    }
}
