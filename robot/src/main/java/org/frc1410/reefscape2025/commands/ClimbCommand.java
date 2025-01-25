package org.frc1410.reefscape2025.commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsytems.ClimberMotor;


public class ClimbCommand extends Command {
    private final ClimberMotor climberMotor;

    public ClimbCommand(ClimberMotor climberMotor) {
        this.climberMotor = climberMotor;
        addRequirements(this.climberMotor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.climberMotor.RunClimberMotor(1);
    }

    @Override
    public boolean isFinished() {
        if(this.climberMotor.GetClimbLimitSwitch()) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {

    }
}
