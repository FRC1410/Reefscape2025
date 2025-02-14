package org.frc1410.reefscape2025.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.Climber;

import static org.frc1410.reefscape2025.util.Constants.*;

public class ClimbCommand extends Command {
    private final Climber climber;
    private final Axis rightaxis;
    private final Axis leftaxis;

    public ClimbCommand(Climber climber, Axis rightaxis, Axis Leftaxis) {
        this.climber = climber;
        this.rightaxis = rightaxis;
        this.leftaxis = Leftaxis;
        addRequirements(climber);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double rightTrigger = this.rightaxis.get();
        double leftTrigger = this.leftaxis.get();
        this.climber.setClimberSpeed((rightTrigger - leftTrigger) * CLIMBER_MOTOR_MAX_SPEED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.climber.setClimberSpeed(0);
    }
}
