package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.config.PIDConstants;

public final class Tuning {

    // Drivetrain
    public static final double SWERVE_DRIVE_P = 0.401;
    public static final double SWERVE_DRIVE_I = 0;
    public static final double SWERVE_DRIVE_D = 0;

    public static final double SWERVE_STEER_P = 5.3;
    public static final double SWERVE_STEER_I = 0.0;
    public static final double SWERVE_STEER_D = 0.05;

//    public static final double DRIVE_KS = 0.31720;
//    public static final double DRIVE_KV = 0.12280;
    public static final double DRIVE_KS = 0.13456;
    public static final double DRIVE_KV = 0.11551;

    // Path following
    public static final PIDConstants PATH_AUTO_TRANSLATION_CONSTRAINTS = new PIDConstants(3.6, 0, 0);
    public static final PIDConstants PATH_AUTO_ROTATION_CONSTRAINTS = new PIDConstants(0.6, 0, 0.05);
}