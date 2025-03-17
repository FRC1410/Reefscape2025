package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;

import org.frc1410.reefscape2025.subsystems.CoralRotation;
import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;
import org.frc1410.reefscape2025.subsystems.SuperStructure;

public class IntakeCoral extends Command {
    private final Elevator elevator;
    private final CoralRotation coralRotation;
    private final LBozo lBozo;
    private final LEDs leds;

    public IntakeCoral(Elevator elevator, CoralRotation coralRotation, LBozo lBozo, LEDs leds) {
        this.elevator = elevator;
        this.coralRotation = coralRotation;
        this.lBozo = lBozo;
        this.leds = leds;
        addRequirements(this.lBozo);
    }

    @Override
    public void initialize() {
        this.leds.setColor(LEDs.Color.ORANGE);
    }

    @Override
    public void execute() {
        this.coralRotation.setDesiredCoralRotation(SuperStructure.INTAKE);

        this.lBozo.setLBozoSpeed(-0.5);
        this.lBozo.setOuttakeSpeed(0.18);
    }

    @Override
    public boolean isFinished() {
        return this.lBozo.hasCoral();
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
        this.lBozo.setOuttakeSpeed(0);
        this.elevator.resetElevatorEncoder();
        this.coralRotation.setDesiredCoralRotation(SuperStructure.HOME);
    }
}
