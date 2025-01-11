package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.config.PIDConstants;

public final class Tuning {

    // Drivetrain
    public static final double SWERVE_DRIVE_P = 0;
    public static final double SWERVE_DRIVE_I = 0;
    public static final double SWERVE_DRIVE_D = 0;

    public static final double SWERVE_STEER_P = 2;
    public static final double SWERVE_STEER_I = 0;
    public static final double SWERVE_STEER_D = 0;

    public static final double DRIVE_KS = 0.31720;
    public static final double DRIVE_KV = 0.12280;

    // Path following
    public static final PIDConstants PATH_AUTO_TRANSLATION_CONSTRAINTS = new PIDConstants(4, 0, 0);
    public static final PIDConstants PATH_AUTO_ROTATION_CONSTRAINTS = new PIDConstants(1, 0, 0);
}