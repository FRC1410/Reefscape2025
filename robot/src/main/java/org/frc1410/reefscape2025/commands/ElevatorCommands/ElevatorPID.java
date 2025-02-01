package org.frc1410.reefscape2025.commands.ElevatorCommands;
import com.pathplanner.lib.config.PIDConstants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;

import static org.frc1410.reefscape2025.util.Tuning.*;

public class ElevatorPID extends Command {
    private PIDController PID;

    private final Elevator elevator;


    public ElevatorPID(Elevator elevator) {
        this.elevator = elevator;


    }

    @Override
    public void initialize() {
        PID = new PIDController(ELEVEATOR_P, ELEVEATOR_I, ELEVEATOR_D);

        PID.setSetpoint(elevator.getDesiredElevatorState());

        PID.setTolerance(ELEVATOR_TOLERANCE);
    }
    
    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return PID.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        elevator.elevatorSpeed(0);
    }

}