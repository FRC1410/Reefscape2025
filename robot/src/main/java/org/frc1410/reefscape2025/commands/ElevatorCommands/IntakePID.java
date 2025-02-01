package org.frc1410.reefscape2025.commands.ElevatorCommands;

import org.frc1410.reefscape2025.subsystems.Elevator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import static org.frc1410.reefscape2025.util.Tuning.*;

public class IntakePID extends Command{
    private final Elevator elevator;
    private PIDController PID;

    public IntakePID(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void initialize() {
        PID = new PIDController(INTAKE_P, INTAKE_I, INTAKE_D);

        PID.setSetpoint(elevator.getDesiredIntakeRotation());

        PID.setTolerance(INTAKE_TOLERANCE);
    }
    
    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return PID.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        elevator.intakeSpeed(0);
    }
}
