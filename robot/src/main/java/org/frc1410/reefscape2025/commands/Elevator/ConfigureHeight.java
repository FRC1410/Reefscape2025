package org.frc1410.reefscape2025.commands.Elevator;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Elevator;

public class ConfigureHeight extends Command {
    private final Elevator elevator;
    private final Elevator.ELEVATOR_STATE desiredElevatorState;

    public ConfigureHeight(Elevator elevator, Elevator.ELEVATOR_STATE desiredElevatorState) {
        this.elevator = elevator;
        this.desiredElevatorState = desiredElevatorState;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        this.elevator.setDesiredElevatorState(desiredElevatorState);
        
    }
    
    @Override
    public void execute() {
        this.elevator.goToDesiredAngle();

        // TODO: LED code in here
    }

    @Override
    public boolean isFinished() {
        return this.elevator.IntakeRotationAtSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setIntakeRotationVolatgeToZero();
        System.out.println("command has ended");
        System.out.println("command has ended");
        System.out.println("command has ended");
        System.out.println("command has ended");
        System.out.println("command has ended");

    }

}