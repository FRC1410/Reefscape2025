package org.frc1410.reefscape2025.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.Drivetrain;

import static org.frc1410.reefscape2025.util.Constants.*;

public class DriveLooped extends Command {
    private final Drivetrain drivetrain;

    private final Axis xAxis;
    private final Axis yAxis;

    private final Axis rotationAxis;

    private final Axis robotRelativeTrigger;

    public DriveLooped(Drivetrain drivetrain, Axis xAxis, Axis yAxis, Axis rotationAxis, Axis robotRelativeTrigger) {
        this.drivetrain = drivetrain;

        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.rotationAxis = rotationAxis;
        this.robotRelativeTrigger = robotRelativeTrigger;

        this.addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        var xVelocity = SWERVE_DRIVE_MAX_SPEED.times(-this.xAxis.get());
        var yVelocity = SWERVE_DRIVE_MAX_SPEED.times(-this.yAxis.get());
        var angularVelocity = SWERVE_DRIVE_MAX_ANGULAR_VELOCITY.times(-this.rotationAxis.get());

        this.drivetrain.drive(new ChassisSpeeds(xVelocity, yVelocity, angularVelocity));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
