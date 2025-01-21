package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;

import java.util.List;

import static edu.wpi.first.units.Units.*;
import static org.frc1410.reefscape2025.util.Tuning.*;

public final class Constants {
    // Robot constants
    public static final double DRIVE_GEAR_RATIO = (50.0 / 16.0) * (17.0 / 27.0) * (45.0 / 15.0);
//    public static final double DRIVE_L_TWO_GEAR_RATIO = (50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0);
    public static final Measure<DistanceUnit> WHEEL_RADIUS = Units.Inches.of(2);
    public static final Measure<DistanceUnit> WHEEL_CIRCUMFERENCE = WHEEL_RADIUS.times(2 * Math.PI);
    public static final Measure<DistanceUnit> TRACKWIDTH_METERS = Meters.of(0.6032627);

    // Drive constants
    public static final Angle FRONT_LEFT_STEER_ENCODER_OFFSET = Degrees.of(141.503906);
    public static final Angle FRONT_RIGHT_STEER_ENCODER_OFFSET = Degrees.of(322.031250);
    public static final Angle BACK_LEFT_STEER_ENCODER_OFFSET = Degrees.of(269.033203);
    public static final Angle BACK_RIGHT_STEER_ENCODER_OFFSET = Degrees.of(333.544922);

    public static final boolean FRONT_LEFT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean FRONT_RIGHT_DRIVE_MOTOR_INVERTED = true;
    public static final boolean BACK_LEFT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean BACK_RIGHT_DRIVE_MOTOR_INVERTED = true;

    public static final boolean FRONT_LEFT_STEER_MOTOR_INVERTED = true;
    public static final boolean FRONT_RIGHT_STEER_MOTOR_INVERTED = false;
    public static final boolean BACK_LEFT_STEER_MOTOR_INVERTED = true;
    public static final boolean BACK_RIGHT_STEER_MOTOR_INVERTED = true;

    public static final Translation2d FRONT_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, 0.301625);
    public static final Translation2d FRONT_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, 0.301625);
    public static final Translation2d BACK_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, -0.301625);
    public static final Translation2d BACK_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, -0.301625);

//    public static final Translation2d FRONT_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, 0.301625);
//    public static final Translation2d FRONT_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, -0.301625);
//    public static final Translation2d BACK_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, 0.301625);
//    public static final Translation2d BACK_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, -0.301625);

    public static final SwerveDriveKinematics SWERVE_DRIVE_KINEMATICS = new SwerveDriveKinematics(
            FRONT_LEFT_SWERVE_MODULE_LOCATION,
            FRONT_RIGHT_SWERVE_MODULE_LOCATION,
            BACK_LEFT_SWERVE_MODULE_LOCATION,
            BACK_RIGHT_SWERVE_MODULE_LOCATION
    );

    public static final double slowMultiplier = 0.36;
    public static final LinearVelocity SWERVE_DRIVE_MAX_SPEED = MetersPerSecond.of(5.5 * slowMultiplier);
    public static final AngularVelocity SWERVE_DRIVE_MAX_ANGULAR_VELOCITY = DegreesPerSecond.of(570 * slowMultiplier);

    public static final double DRIVE_MOTOR_CURRENT_LIMIT = 40;
    public static final int STEER_MOTOR_CURRENT_LIMIT = 30;

    public static PPHolonomicDriveController HOLONOMIC_AUTO_CONFIG = new PPHolonomicDriveController(
            PATH_AUTO_TRANSLATION_CONSTRAINTS,
            PATH_AUTO_ROTATION_CONSTRAINTS
    );

   public static final List<ScoringPath> SCORING_POSITIONS_BLUE = List.of();
   public static final List<ScoringPath> SCORING_POSITIONS_RED = SCORING_POSITIONS_BLUE.stream().map((position) -> new ScoringPath()).toList();
}