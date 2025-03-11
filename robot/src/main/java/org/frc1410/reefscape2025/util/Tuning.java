package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.config.PIDConstants;

public final class Tuning {
    // Elevator

    public static double ELEVEATOR_P = 0.004; //0.004
    public static double ELEVEATOR_I = 0;
    public static double ELEVEATOR_D = 0;

    public static double ELEVATOR_TOLERANCE = 50;

    public static double CORAL_ANGLE_P = /*Anthony*/6; //6//10
    public static double CORAL_ANGLE_I = 0; //0.5
    public static double CORAL_ANGLE_D = 0; //0.8
    
    public static double CORAL_ANGLE_TOLERANCE = 0.05;


    // Drivetrain
    public static final double SWERVE_DRIVE_P = 0.401;
    public static final double SWERVE_DRIVE_I = 0;
    public static final double SWERVE_DRIVE_D = 0;

    public static final double SWERVE_STEER_P = 4.7;
    public static final double SWERVE_STEER_I = 0.0;
    public static final double SWERVE_STEER_D = 0.1;

//    public static final double SWERVE_STEER_P = 0;
//    public static final double SWERVE_STEER_I = 0.0;
//    public static final double SWERVE_STEER_D = 0.0;

    public static final double DRIVE_KS = 0.36498;
    public static final double DRIVE_KV = 0.11769;

    public static double DRIVE_MULTIPLIER = 1.0;

    // Path following
    public static final PIDConstants PATH_AUTO_TRANSLATION_CONSTRAINTS = new PIDConstants(3.6, 0, 0);
    public static final PIDConstants PATH_AUTO_ROTATION_CONSTRAINTS = new PIDConstants(0.6, 0, 0.05);

    public static final PIDConstants PATH_FOLLOWING_TRANSLATION_CONSTRAINTS = new PIDConstants(4, 0, 0);
    public static final PIDConstants PATH_FOLLOWING_ROTATION_CONSTRAINTS = new PIDConstants(0.6, 0, 0.05);
}