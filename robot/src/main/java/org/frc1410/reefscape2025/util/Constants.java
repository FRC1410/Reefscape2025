package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.*;
import edu.wpi.first.wpilibj.DriverStation;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

import static edu.wpi.first.units.Units.*;
import static org.frc1410.reefscape2025.util.Tuning.*;
import static org.frc1410.reefscape2025.util.ReefPaths.*;

public final class Constants {

    public static RobotConfig ROBOT_CONFIG;

    static {
        try {
            ROBOT_CONFIG = RobotConfig.fromGUISettings();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Robot constants
    public static final double DRIVE_GEAR_RATIO = (50.0 / 16.0) * (17.0 / 27.0) * (45.0 / 15.0);
//    public static final double DRIVE_L_TWO_GEAR_RATIO = (50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0);
    public static final Measure<DistanceUnit> WHEEL_RADIUS = Units.Inches.of(2);
    public static final Measure<DistanceUnit> WHEEL_CIRCUMFERENCE = WHEEL_RADIUS.times(2 * Math.PI);
    public static final Measure<DistanceUnit> TRACKWIDTH_METERS = Meters.of(0.6032627);

    // Drive constants
    public static final Angle FRONT_LEFT_STEER_ENCODER_OFFSET = Degrees.of(-52.207031 + 90);
    public static final Angle FRONT_RIGHT_STEER_ENCODER_OFFSET = Degrees.of(62.138672 + 90);
    public static final Angle BACK_LEFT_STEER_ENCODER_OFFSET = Degrees.of(-128.408203 - 90);
    public static final Angle BACK_RIGHT_STEER_ENCODER_OFFSET = Degrees.of(178.769531 - 90);

    public static final boolean FRONT_LEFT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean FRONT_RIGHT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean BACK_LEFT_DRIVE_MOTOR_INVERTED = false;
    public static final boolean BACK_RIGHT_DRIVE_MOTOR_INVERTED = true;

    public static final boolean FRONT_LEFT_STEER_MOTOR_INVERTED = true;
    public static final boolean FRONT_RIGHT_STEER_MOTOR_INVERTED = true;
    public static final boolean BACK_LEFT_STEER_MOTOR_INVERTED = true;
    public static final boolean BACK_RIGHT_STEER_MOTOR_INVERTED = true;

    public static final Translation2d FRONT_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, 0.301625);
    public static final Translation2d FRONT_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(0.301625, -0.301625);
    public static final Translation2d BACK_LEFT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, 0.301625);
    public static final Translation2d BACK_RIGHT_SWERVE_MODULE_LOCATION = new Translation2d(-0.301625, -0.301625);

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

    public static final double slowMultiplier = 0.5;
    public static final LinearVelocity SWERVE_DRIVE_MAX_SPEED = MetersPerSecond.of(5.5 * slowMultiplier);
    public static final AngularVelocity SWERVE_DRIVE_MAX_ANGULAR_VELOCITY = DegreesPerSecond.of(570 * slowMultiplier);
    public static final LinearAcceleration SWERVE_DRIVE_MAX_ACCELERATION = MetersPerSecondPerSecond.of(6 * slowMultiplier);
    public static final AngularAcceleration SWERVE_DRIVE_MAX_ANGULAR_ACCELERATION = DegreesPerSecondPerSecond.of(1062 * slowMultiplier);

    public static final double DRIVE_MOTOR_CURRENT_LIMIT = 40;
    public static final int STEER_MOTOR_CURRENT_LIMIT = 30;

    public static PPHolonomicDriveController HOLONOMIC_AUTO_CONFIG = new PPHolonomicDriveController(
            PATH_AUTO_TRANSLATION_CONSTRAINTS,
            PATH_AUTO_ROTATION_CONSTRAINTS
    );

    public static PathConstraints PATH_FINDING_CONSTRAINTS = new PathConstraints(
            SWERVE_DRIVE_MAX_SPEED,
            SWERVE_DRIVE_MAX_ACCELERATION,
            SWERVE_DRIVE_MAX_ANGULAR_VELOCITY,
            SWERVE_DRIVE_MAX_ANGULAR_ACCELERATION
    );

    public static PPHolonomicDriveController PATH_FOLLOWING_CONTROLLER = new PPHolonomicDriveController(
            PATH_FOLLOWING_TRANSLATION_CONSTRAINTS,
            PATH_FOLLOWING_ROTATION_CONSTRAINTS
    );
    public static final List<ScoringPath> SCORING_POSITIONS_BLUE = List.of();
    public static final List<ScoringPath> SCORING_POSITIONS_RED = SCORING_POSITIONS_BLUE.stream().map((position) -> new ScoringPath()).toList();

    public static final List<ReefSides> BLUE_REEF = List.of(
            BLUE_NORTH_REEF,
            BLUE_NORTH_WEST_REEF,
            BLUE_SOUTH_REEF,
            BLUE_SOUTH_WEST_REEF
    );

    public static final List<ReefSides> RED_REEF = List.of();

    public static final String REEF_CAMERA = "Arducam_OV9281_USB_Camera";
    public static final String SOURCE_CAMERA = "Arducam_OV9281_USB_Camera 2";

    public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
    public static final Transform3d CAMERA_POSE = new Transform3d(
            new Translation3d(-0.0762,0.0127,0.22225),
            new Rotation3d(0,0,0)
    );
}