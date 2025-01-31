package org.frc1410.reefscape2025.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Climber;

import static org.frc1410.reefscape2025.util.Constants.CLIMBER_MOTOR_INVERTED_MAX_SPEED;

public class UnClimb extends Command {
    private final Climber climber;

    public UnClimb(Climber climber) {
        this.climber = climber;
        addRequirements(this.climber);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.climber.setClimberSpeed(CLIMBER_MOTOR_INVERTED_MAX_SPEED);
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
