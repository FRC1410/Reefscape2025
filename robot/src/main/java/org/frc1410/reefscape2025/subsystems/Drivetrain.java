package org.frc1410.reefscape2025.subsystems;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import org.frc1410.framework.scheduler.subsystem.SubsystemStore;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.reefscape2025.util.NetworkTables;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static org.frc1410.reefscape2025.util.Constants.*;
import static org.frc1410.reefscape2025.util.IDs.*;

public class Drivetrain implements TickedSubsystem {
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Drivetrain");

    private final DoublePublisher frontLeftVelocitySetpoint = NetworkTables.PublisherFactory(this.table, "Front Left Velocity Setpoint", 0);
    private final DoublePublisher frontRightVelocitySetpoint = NetworkTables.PublisherFactory(this.table, "Front Right Velocity Setpoint", 0);
    private final DoublePublisher backLeftVelocitySetpoint = NetworkTables.PublisherFactory(this.table, "Back Left Velocity Setpoint", 0);
    private final DoublePublisher backRightVelocitySetpoint = NetworkTables.PublisherFactory(this.table, "Back Right Velocity Setpoint", 0);

    private final DoublePublisher frontLeftAngleSetpoint = NetworkTables.PublisherFactory(this.table, "Front Left Angle Setpoint", 0);
    private final DoublePublisher frontRightAngleSetpoint = NetworkTables.PublisherFactory(this.table, "Front Right Angle Setpoint", 0);
    private final DoublePublisher backLeftAngleSetpoint = NetworkTables.PublisherFactory(this.table, "Back Left Angle Setpoint", 0);
    private final DoublePublisher backRightAngleSetpoint = NetworkTables.PublisherFactory(this.table, "Back Right Angle Setpoint", 0);

    private final DoublePublisher frontLeftObservedVelocity = NetworkTables.PublisherFactory(this.table, "Front Left Observed Velocity", 0);
    private final DoublePublisher frontRightObservedVelocity = NetworkTables.PublisherFactory(this.table, "Front Right Observed Velocity", 0);
    private final DoublePublisher backLeftObservedVelocity = NetworkTables.PublisherFactory(this.table, "Back Left Observed Velocity", 0);
    private final DoublePublisher backRightObservedVelocity = NetworkTables.PublisherFactory(this.table, "Back Right Observed Velocity", 0);

    private final DoublePublisher frontLeftObservedAngle = NetworkTables.PublisherFactory(this.table, "Front Left Observed Angle", 0);
    private final DoublePublisher frontRightObservedAngle = NetworkTables.PublisherFactory(this.table, "Front Right Observed Angle", 0);
    private final DoublePublisher backLeftObservedAngle = NetworkTables.PublisherFactory(this.table, "Back Left Observed Angle", 0);
    private final DoublePublisher backRightObservedAngle = NetworkTables.PublisherFactory(this.table, "Back Right Observed Angle", 0);

    private final DoublePublisher poseX = NetworkTables.PublisherFactory(this.table, "X position", 0);
    private final DoublePublisher poseY = NetworkTables.PublisherFactory(this.table, "y position", 0);
    private final DoublePublisher heading = NetworkTables.PublisherFactory(this.table, "Heading", 0);

    private final DoublePublisher yaw = NetworkTables.PublisherFactory(this.table, "yaw", 0);
    private final DoublePublisher pitch = NetworkTables.PublisherFactory(this.table, "pitch", 0);
    private final DoublePublisher roll = NetworkTables.PublisherFactory(this.table, "roll", 0);

    private final DoublePublisher characterizationVolts = NetworkTables.PublisherFactory(this.table,
            "characterization volts", 0);

    private final StructPublisher<Pose2d> posePublisher = NetworkTableInstance.getDefault()
            .getStructTopic("pose", Pose2d.struct).publish();

    private final StructPublisher<Pose2d> encoderOnlyPosePublisher = NetworkTableInstance.getDefault()
            .getStructTopic("encoderOnlyPose", Pose2d.struct).publish();

    private final SwerveModule frontLeftModule;
    private final SwerveModule backLeftModule;
    private final SwerveModule frontRightModule;
    private final SwerveModule backRightModule;

//    private final SwerveDrivePoseEstimator poseEstimator;

    private double previousPipelineTimestamp = 0;

    private Rotation2d fieldRelativeOffset = new Rotation2d();

    private boolean hasSeenAprilTag = false;

    public Drivetrain(SubsystemStore subsystems) {
        this.frontLeftModule = subsystems.track(new SwerveModule(
                FRONT_LEFT_DRIVE_MOTOR,
                FRONT_LEFT_STEER_MOTOR,
                FRONT_LEFT_STEER_ENCODER,
                FRONT_LEFT_DRIVE_MOTOR_INVERTED,
                FRONT_LEFT_STEER_MOTOR_INVERTED,
                FRONT_LEFT_STEER_ENCODER_OFFSET,
                this.frontLeftVelocitySetpoint,
                this.frontLeftAngleSetpoint,
                this.frontLeftObservedVelocity,
                this.frontLeftObservedAngle
        ));

        this.frontRightModule = subsystems.track(new SwerveModule(
                FRONT_RIGHT_DRIVE_MOTOR,
                FRONT_RIGHT_STEER_MOTOR,
                FRONT_RIGHT_STEER_ENCODER,
                FRONT_RIGHT_DRIVE_MOTOR_INVERTED,
                FRONT_RIGHT_STEER_MOTOR_INVERTED,
                FRONT_RIGHT_STEER_ENCODER_OFFSET,
                this.frontRightVelocitySetpoint,
                this.frontRightAngleSetpoint,
                this.frontRightObservedVelocity,
                this.frontRightObservedAngle
        ));

        this.backLeftModule = subsystems.track(new SwerveModule(
                BACK_LEFT_DRIVE_MOTOR,
                BACK_LEFT_STEER_MOTOR,
                BACK_LEFT_STEER_ENCODER,
                BACK_LEFT_DRIVE_MOTOR_INVERTED,
                BACK_LEFT_STEER_MOTOR_INVERTED,
                BACK_LEFT_STEER_ENCODER_OFFSET,
                this.backLeftVelocitySetpoint,
                this.backLeftAngleSetpoint,
                this.backLeftObservedVelocity,
                this.backLeftObservedAngle
        ));

        this.backRightModule = subsystems.track(new SwerveModule(
                BACK_RIGHT_DRIVE_MOTOR,
                BACK_RIGHT_STEER_MOTOR,
                BACK_RIGHT_STEER_ENCODER,
                BACK_RIGHT_DRIVE_MOTOR_INVERTED,
                BACK_RIGHT_STEER_MOTOR_INVERTED,
                BACK_RIGHT_STEER_ENCODER_OFFSET,
                this.backRightVelocitySetpoint,
                this.backRightAngleSetpoint,
                this.backRightObservedVelocity,
                this.backRightObservedAngle
        ));

//        this.poseEstimator = new SwerveDrivePoseEstimator(
//                SWERVE_DRIVE_KINEMATICS,
//                this.getGyroYaw(),
//                this.getSwerveModulePositions(),
//                new Pose2d()
//        );
    }

    public void drive(ChassisSpeeds chassisSpeeds) {
        var swerveModuleStates = SWERVE_DRIVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SWERVE_DRIVE_MAX_SPEED.in(MetersPerSecond));

        this.frontLeftModule.setDesiredState(swerveModuleStates[0]);
        this.frontRightModule.setDesiredState(swerveModuleStates[1]);
        this.backLeftModule.setDesiredState(swerveModuleStates[2]);
        this.backRightModule.setDesiredState(swerveModuleStates[3]);
    }

    public void drive(Voltage voltage) {
        this.characterizationVolts.set(voltage.in(Volts));

        frontLeftModule.drive(voltage);
        frontRightModule.drive(voltage);
        backLeftModule.drive(voltage);
        backRightModule.drive(voltage);
    }

    public ChassisSpeeds getChassisSpeeds() {
        return SWERVE_DRIVE_KINEMATICS.toChassisSpeeds(
                this.frontLeftModule.getState(),
                this.frontRightModule.getState(),
                this.backLeftModule.getState(),
                this.backRightModule.getState());
    }

    private SwerveModulePosition[] getSwerveModulePositions() {
        return new SwerveModulePosition[] {
                this.frontLeftModule.getPosition(),
                this.frontRightModule.getPosition(),
                this.backLeftModule.getPosition(),
                this.backRightModule.getPosition()
        };
    }

    public AngularVelocity getAverageDriveAngularVelocity() {
        return this.frontLeftModule.getAngularVelocity()
                .plus(this.frontRightModule.getAngularVelocity())
                .plus(this.backLeftModule.getAngularVelocity())
                .plus(this.backRightModule.getAngularVelocity())
                .div(4);
    }

    @Override
    public void periodic() {
    }
}
