package org.frc1410.reefscape2025.commands.Drivetrain;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.control.Axis;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.subsystems.Elevator;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static org.frc1410.reefscape2025.util.Constants.*;
import static org.frc1410.reefscape2025.util.Tuning.*;

public class DriveLooped extends Command {
    private final Drivetrain drivetrain;
    private final Elevator elevator;

    private final Axis xAxis;
    private final Axis yAxis;

    private final Axis rotationAxis;

    private final Axis robotRelativeTrigger;

    public DriveLooped(Drivetrain drivetrain, Elevator elevator, Axis xAxis, Axis yAxis, Axis rotationAxis, Axis robotRelativeTrigger) {
        this.drivetrain = drivetrain;
        this.elevator = elevator;

        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.rotationAxis = rotationAxis;
        this.robotRelativeTrigger = robotRelativeTrigger;

        this.addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        LinearVelocity xVelocity;
        LinearVelocity yVelocity;
        AngularVelocity angularVelocity;

        switch (elevator.desiredElevatorHeightConfirmed) {
            case 1890, 3600, 6218 -> DRIVE_MULTIPLIER = 0.25;
            case 12080 -> DRIVE_MULTIPLIER = 0.1;
            default -> DRIVE_MULTIPLIER = 1.0;
        }

        if(this.drivetrain.isSlowModeEnabled()) {
            xVelocity = SWERVE_DRIVE_MAX_SPEED.times(-this.xAxis.get() * 0.6);
            yVelocity = SWERVE_DRIVE_MAX_SPEED.times(-this.yAxis.get() * 0.6);
            angularVelocity = SWERVE_DRIVE_MAX_ANGULAR_VELOCITY.times(-this.rotationAxis.get() * 0.6);
        } else {
            xVelocity = SWERVE_DRIVE_MAX_SPEED.times(-this.xAxis.get());
            yVelocity = SWERVE_DRIVE_MAX_SPEED.times(-this.yAxis.get());
            angularVelocity = SWERVE_DRIVE_MAX_ANGULAR_VELOCITY.times(-this.rotationAxis.get());
        }

//        drivetrain.drive(new ChassisSpeeds(xVelocity, yVelocity, angularVelocity));

        if(robotRelativeTrigger.button().isActive()) {
            drivetrain.drive(new ChassisSpeeds(xVelocity, yVelocity, angularVelocity));
        } else {
            this.drivetrain.fieldOrientedDrive(
                    new ChassisSpeeds(
                            xVelocity.in(MetersPerSecond),
                            yVelocity.in(MetersPerSecond),
                            angularVelocity.in(RadiansPerSecond)
                    ).times(DRIVE_MULTIPLIER)
            );
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
