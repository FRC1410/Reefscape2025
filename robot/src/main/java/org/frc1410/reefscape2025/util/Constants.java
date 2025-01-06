package org.frc1410.reefscape2025.util;

import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;

public final class Constants {
    // Robot constants
    public static final double DRIVE_GEAR_RATIO = (50.0 / 16.0) * (17.0 / 27.0) * (45.0 / 15.0);
    public static final Measure<DistanceUnit> WHEEL_RADIUS = Units.Inches.of(2);
    public static final Measure<DistanceUnit> WHEEL_CIRCUMFERENCE = WHEEL_RADIUS.times(2 * Math.PI);

    // Drive constants
    public static final double SWERVE_DRIVE_P = 0;
    public static final double SWERVE_DRIVE_I = 0;
    public static final double SWERVE_DRIVE_D = 0;

    public static final double SWERVE_STEER_P = 0;
    public static final double SWERVE_STEER_I = 0;
    public static final double SWERVE_STEER_D = 0;

    public static final double DRIVE_KS = 0;
    public static final double DRIVE_KV = 0;

    public static final double DRIVE_MOTOR_CURRENT_LIMIT = 40;
    public static final int STEER_MOTOR_CURRENT_LIMIT = 30;
}