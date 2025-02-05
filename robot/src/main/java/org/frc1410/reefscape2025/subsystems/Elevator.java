package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Encoder;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.reefscape2025.util.NetworkTables;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Tuning.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public class Elevator implements TickedSubsystem {
    private final TalonFX leftMotor;
    private final TalonFX rightMotor;
    private final SparkMax intakeAngleMotor;
    private final Encoder intakeAngleEncoder;
    private final Encoder barroonEncoder;

    private final PIDController elevatorPIDController = new PIDController(
            ELEVEATOR_P,
            ELEVEATOR_I,
            ELEVEATOR_D
    );

    private final PIDController intakeAnglePIDController = new PIDController(
            INTAKE_ANGLE_P,
            INTAKE_ANGLE_I,
            INTAKE_ANGLE_D
    );

    private Distance desiredElevatorHeight;
    private Angle desiredElevatorAngle;

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Elevator");

    private final DoublePublisher desiredHeightPub = NetworkTables.PublisherFactory(this.table, "Desired Height", 0);
    private final DoublePublisher desiredAnglePub = NetworkTables.PublisherFactory(this.table, "Desired Angle", 0);

    private final DoublePublisher actualElevatorHeightPub = NetworkTables.PublisherFactory(this.table, "Actual Elevator Height", 0);
    private final DoublePublisher actualElevatorAnglePub = NetworkTables.PublisherFactory(this.table, "Actual Elevator Angle", 0);

    public Elevator() {
        this.leftMotor = new TalonFX(LEFT_ELEVATOR_MOTOR);
        this.rightMotor = new TalonFX(RIGHT_ELEVATOR_MOTOR);

        var leftMotorConfig = new TalonFXConfiguration();
        leftMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        leftMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        leftMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        leftMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        this.leftMotor.getConfigurator().apply(leftMotorConfig);

        var rightMotorConfig = new TalonFXConfiguration();
        rightMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        rightMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        rightMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        rightMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        this.rightMotor.getConfigurator().apply(rightMotorConfig);

        this.intakeAngleMotor = new SparkMax(INTAKE_ROTATION_MOTOR, SparkLowLevel.MotorType.kBrushless);

        var intakeAngleMotorConfig = new SparkMaxConfig();

        intakeAngleMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        intakeAngleMotorConfig.smartCurrentLimit(30);
        intakeAngleMotorConfig.inverted(INTAKE_ANGLE_ROTATION_MOTOR_INVERTED);

        this.intakeAngleMotor.configure(
                intakeAngleMotorConfig,
                SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters
        );

        this.intakeAngleEncoder = new Encoder(INTAKE_ANGLE_ENCODER_CHANNEL_A, INTAKE_ANGLE_ENCODER_CHANNEL_B, true);
        this.intakeAngleEncoder.reset();

        this.barroonEncoder = new Encoder(ELEVATOR_HEIGHT_ENCODER_CHANNEL_A, ELEVATOR_HEIGHT_ENCODER_CHANNEL_B, true);
        this.barroonEncoder.reset();
    }

    public void setManualSpeed(double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public void setDesiredElevatorState(ELEVATOR_STATE desiredElevatorState) {
        this.desiredElevatorHeight = desiredElevatorState.getElevatorDistance();
        this.desiredElevatorAngle = desiredElevatorState.getElevatorAngle();
    }

    public void goToDesiredHeight() {
        var motorOutput = this.elevatorPIDController.calculate(
                this.getCurrentElevatorDistance().baseUnitMagnitude(),
                this.desiredElevatorHeight.baseUnitMagnitude());

        this.leftMotor.setVoltage(motorOutput);
        this.rightMotor.setVoltage(motorOutput);
    }

    public void goToDesiredAngle() {
        var motorOutput = this.intakeAnglePIDController.calculate(
                this.getCurrentIntakeAngle().baseUnitMagnitude(),
                this.desiredElevatorAngle.baseUnitMagnitude()
        );

        this.intakeAngleMotor.setVoltage(motorOutput);
    }

    public Distance getCurrentElevatorDistance() {
        var totalCounts = this.barroonEncoder.get();
        return Units.Meters.of(totalCounts / (8192 * ELEVATOR_GEAR_RATIO) * ENCODER_SHAFT_CIRCUMFERENCE.in(Units.Meters));
    }

    public Angle getCurrentIntakeAngle() {
        var totalCounts = this.intakeAngleEncoder.get();
        return Units.Degree.of(totalCounts / (8192 * INTAKE_ANGLE_GEAR_RATIO) * 360);
    }

    public enum ELEVATOR_STATE {
        L1(L_1_HEIGHT, L1_ANGLE),
        L2(L_2_HEIGHT, L2_ANGLE),
        L3(L_3_HEIGHT, L3_ANGLE),
        L4(L_4_HEIGHT, L4_ANGLE),
        INTAKE(INTAKE_HEIGHT, INTAKE_ANGLE),
        HOME(HOME_HEIGHT, HOME_ANGLE);

        private final Distance elevatorDistance;
        private final Angle elevatorAngle;

        ELEVATOR_STATE(Distance elevatorDistance, Angle elevatorAngle) {
            this.elevatorDistance = elevatorDistance;
            this.elevatorAngle = elevatorAngle;
        }

        public Distance getElevatorDistance() {
            return elevatorDistance;
        }
        public Angle getElevatorAngle() {
            return elevatorAngle;
        }
    }

    @Override
    public void periodic() {
        this.desiredHeightPub.set(this.desiredElevatorHeight.baseUnitMagnitude());
        this.desiredAnglePub.set(this.desiredElevatorAngle.baseUnitMagnitude());

        this.actualElevatorHeightPub.set(this.getCurrentElevatorDistance().baseUnitMagnitude());
        this.actualElevatorAnglePub.set(this.getCurrentIntakeAngle().baseUnitMagnitude());
    }
}
