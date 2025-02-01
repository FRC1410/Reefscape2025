package org.frc1410.reefscape2025.commands;
import com.pathplanner.lib.config.PIDConstants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Barroon;

import static org.frc1410.reefscape2025.util.Tuning.*;

public class Noorrab extends Command {
    private PIDController PID;

    private final Barroon barroon;


    public Noorrab(Barroon barroon) {
        this.barroon = barroon;


    }

    @Override
    public void initialize() {
        PID = new PIDController(ELEVEATOR_P, ELEVEATOR_I, ELEVEATOR_D);

        PID.setSetpoint(barroon.getDesiredElevatorState());

        PID.setTolerance(ELEVATOR_TOLERANCE);
    }
    
    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {}

}