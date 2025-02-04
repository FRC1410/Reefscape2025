package org.frc1410.reefscape2025.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Climber;

import static org.frc1410.reefscape2025.util.Constants.*;

public class ClimbCommand extends Command {
    private final Climber climber;

    public ClimbCommand(Climber climber) {
        this.climber = climber;
        addRequirements(this.climber);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.climber.setClimberSpeed(CLIMBER_MOTOR_MAX_SPEED);
    }

    @Override
    public boolean isFinished() {
        return this.climber.getLimitSwitch();
    }

    @Override
    public void end(boolean interrupted) {
        this.climber.setClimberSpeed(0);
    }
}
