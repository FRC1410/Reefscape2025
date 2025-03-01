package org.frc1410.reefscape2025.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.Climber;

import static org.frc1410.reefscape2025.util.Constants.*;

public class ClimbCommand extends Command {
    private final Climber climber;

    public ClimbCommand(Climber climber) {
        this.climber = climber;
     
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        this.climber.setClimberSpeed(1);
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
        this.climber.setClimberSpeed(0);
    }
}
