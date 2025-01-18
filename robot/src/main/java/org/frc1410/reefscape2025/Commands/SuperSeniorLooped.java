package org.frc1410.reefscape2025.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.SuperSenior;


public class SuperSeniorLooped extends Command {

    private final Axis leftxAxis;
    private final Axis rightxAxis;
    private final SuperSenior superSenior;


    public SuperSeniorLooped(SuperSenior superSenior, Axis leftxAxis, Axis rightxAxis) {
        this.superSenior = superSenior;
        this.leftxAxis = leftxAxis;
        this.rightxAxis = rightxAxis;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(superSenior);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {

    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        this.superSenior.setLeftHugSpeed(this.leftxAxis.get());
        this.superSenior.setRightHugSpeed(this.rightxAxis.get());
    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes -- indicated by
     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Hard coding this command to always
     * return true will result in the command executing once and finishing immediately. It is
     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
     * for such an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    /**
     * The action to take when the command ends. Called when either the command
     * finishes normally -- that is it is called when {@link #isFinished()} returns
     * true -- or when  it is interrupted/canceled. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the command.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {

    }
}
