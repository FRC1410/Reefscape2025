package org.frc1410.reefscape2025.commands.Lbozo;

import edu.wpi.first.wpilibj2.command.Command;

import org.frc1410.reefscape2025.subsystems.Elevator;
import org.frc1410.reefscape2025.subsystems.LBozo;
import org.frc1410.reefscape2025.subsystems.LEDs;

public class OuttakeCoral extends Command {
    private final LBozo lBozo;
    private final LEDs leds;
    private final Elevator elevator;

    public OuttakeCoral(LBozo lBozo, LEDs leds, Elevator elevator) {
        this.lBozo = lBozo;
        this.leds = leds;
        this.elevator = elevator;
        addRequirements(this.lBozo);
        addRequirements(this.elevator);
    }

    @Override
    public void initialize() {
        if(this.lBozo.hasCoral()) {
            this.leds.setColor(LEDs.Color.BLUE);
        } else {
            this.leds.setColor(LEDs.Color.ORANGE);
        }
        if(this.elevator.getDesiredElevatorAngleSetpoint() == 1.15) {
            this.lBozo.setLBozoSpeed(-0.17);
        } else {
            this.lBozo.setLBozoSpeed(-0.4);
        }
       
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.lBozo.setLBozoSpeed(0);
    }
}
