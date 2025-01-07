package org.frc1410.reefscape2025.util;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Velocity;

import static edu.wpi.first.units.Units.*;

public final class Constants {
    // Robot constants
    public static final double DRIVE_GEAR_RATIO = (50.0 / 16.0) * (17.0 / 27.0) * (45.0 / 15.0);
    public static final Measure<DistanceUnit> WHEEL_RADIUS = Units.Inches.of(2);
    public static final Measure<DistanceUnit> WHEEL_CIRCUMFERENCE = WHEEL_RADIUS.times(2 * Math.PI);

    // Drive constants
    public static final Angle FRONT_LEFT_STEER_ENCODER_OFFSET = Degrees.of(0);
    public static final Angle FRONT_RIGHT_STEER_ENCODER_OFFSET = Degrees.of(0);
    public static final Angle BACK_LEFT_STEER_ENCODER_OFFSET = Degrees.of(0);
    public static final Angle BACK_RIGHT_STEER_ENCODER_OFFSET = Degrees.of(0);

    public static final boolean FRONT_LEFT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean FRONT_RIGHT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean BACK_LEFT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean BACK_RIGHT_DRIVE_MOTOR_INVERTED = false;

    public static final boolean FRONT_LEFT_STEER_MOTOR_INVERTED = false;
    public static final boolean FRONT_RIGHT_STEER_MOTOR_INVERTED = false;
    public static final boolean BACK_LEFT_STEER_MOTOR_INVERTED = false;
    public static final boolean BACK_RIGHT_STEER_MOTOR_INVERTED = false;

    public static final Translation2d FRONT_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, .301625);
    public static final Translation2d FRONT_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, -0.301625);
    public static final Translation2d BACK_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, 0.301625);
    public static final Translation2d BACK_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, -0.301625);

    public static final SwerveDriveKinematics SWERVE_DRIVE_KINEMATICS = new SwerveDriveKinematics(
            FRONT_LEFT_SWERVE_MODULE_LOCATION,
            FRONT_RIGHT_SWERVE_MODULE_LOCATION,
            BACK_LEFT_SWERVE_MODULE_LOCATION,
            BACK_RIGHT_SWERVE_MODULE_LOCATION
    );

    public static final LinearVelocity SWERVE_DRIVE_MAX_SPEED = MetersPerSecond.of(5.5);
    public static final AngularVelocity SWERVE_DRIVE_MAX_ANGULAR_VELOCITY = DegreesPerSecond.of(570);

    public static final double DRIVE_MOTOR_CURRENT_LIMIT = 40;
    public static final int STEER_MOTOR_CURRENT_LIMIT = 30;
}