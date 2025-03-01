package org.frc1410.reefscape2025.commands.Elevator.Manual;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class ElevatorManual extends Command {
    private Elevator elevator;
    private boolean isInverted;

    public ElevatorManual(Elevator elevator, boolean isInverted) {
        this.elevator = elevator;
        this.isInverted = isInverted;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        
       

    }

    @Override
    public void execute() {
        if (isInverted) {
            this.elevator.setElevatorSetPoint(1000);
        } else {
            this.elevator.setElevatorSetPoint(-1000);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if(!this.isInverted) {
            this.elevator.resetElevatorEncoder();
            this.elevator.setElevatorSetPoint(0);
        }
        
    }
}
