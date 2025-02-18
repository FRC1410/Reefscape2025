package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.AlternateEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.reefscape2025.util.NetworkTables;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Tuning.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public class Elevator implements TickedSubsystem {
    private final SparkMax leftMotor;
    private final SparkMax rightMotor;
    private final SparkMax intakeAngleMotor;

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


    
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Elevator");
    private final NetworkTable intakeTable = NetworkTableInstance.getDefault().getTable("IntakeRotation");

    private final DoublePublisher intakeRotationError = NetworkTables.PublisherFactory(this.intakeTable, "Intake Rotation Error", 0);
    private final DoublePublisher intakePIDSetpoint = NetworkTables.PublisherFactory(this.intakeTable, "Intake PID Setpoint", 0);
    private final DoublePublisher desiredAnglePub = NetworkTables.PublisherFactory(this.intakeTable, "Desired Angle", 0);
    private final DoublePublisher actualElevatorAnglePub = NetworkTables.PublisherFactory(this.intakeTable, "Actual Elevator Angle", 0);
    private final DoublePublisher intakeVolts = NetworkTables.PublisherFactory(this.intakeTable, "Intake Volts", 0);

    private final DoublePublisher desiredHeightPub = NetworkTables.PublisherFactory(this.table, "Desired Height", 0);
    private final DoublePublisher actualElevatorHeightPub = NetworkTables.PublisherFactory(this.table, "Actual Elevator Height", 0);
    private final DoublePublisher elevatorPIDSetpoint = NetworkTables.PublisherFactory(this.table, "Elevator PID Setpoint", 0);
    private final DoublePublisher elevatorRightCurrent = NetworkTables.PublisherFactory(this.table, "Right Elevator Current", 0);
    private final DoublePublisher elevatorLeftCurrent = NetworkTables.PublisherFactory(this.table, "Left Elevator Current", 0);
    private final DoublePublisher actualRightElevatorVolts = NetworkTables.PublisherFactory(this.table, "Actual Right Elevator Volts", 0);
    private final DoublePublisher actualLeftElevatorVolts = NetworkTables.PublisherFactory(this.table, "Actual Left Elevator Volts", 0);
    private final DoublePublisher outputElevatorVolts = NetworkTables.PublisherFactory(this.table, "Desired Elevator Volts", 0);
    

    private int desiredElevatorHeight = 1;
    private int desiredElevatorHeightConfirmed = 1;
    private double desiredElevatorAngle = 0;

    public Elevator() {
        this.leftMotor = new SparkMax(LEFT_ELEVATOR_MOTOR, SparkLowLevel.MotorType.kBrushless);
        
        var leftMotorConfig = new SparkMaxConfig();

        leftMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        leftMotorConfig.smartCurrentLimit(40);

        leftMotorConfig.inverted(false);

        this.leftMotor.configure(
            leftMotorConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters
        );

        this.rightMotor = new SparkMax(RIGHT_ELEVATOR_MOTOR, SparkLowLevel.MotorType.kBrushless);
        
        var rightMotorConfig = new SparkMaxConfig();

        rightMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        rightMotorConfig.smartCurrentLimit(40);

        rightMotorConfig.inverted(true);

        this.rightMotor.configure(
            leftMotorConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters
        );



        this.intakeAngleMotor = new SparkMax(INTAKE_ROTATION_MOTOR, SparkLowLevel.MotorType.kBrushless);

        var intakeAngleMotorConfig = new SparkMaxConfig();

        intakeAngleMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        intakeAngleMotorConfig.smartCurrentLimit(30);

        intakeAngleMotorConfig.inverted(false);

        var alternateEncoderConfig = new AlternateEncoderConfig();
        alternateEncoderConfig.inverted(true);
        intakeAngleMotorConfig.alternateEncoder.apply(alternateEncoderConfig);

        this.intakeAngleMotor.configure(
                intakeAngleMotorConfig,
                SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters
        );

        intakeAngleMotor.getAlternateEncoder().setPosition(0.0);
        

        this.barroonEncoder = new Encoder(ELEVATOR_HEIGHT_ENCODER_CHANNEL_A, ELEVATOR_HEIGHT_ENCODER_CHANNEL_B, false, CounterBase.EncodingType.k4X);
        this.barroonEncoder.reset();

        this.intakeAnglePIDController.setTolerance(INTAKE_TOLERANCE);
        this.elevatorPIDController.setTolerance(ELEVATOR_TOLERANCE);

        elevatorPIDController.setSetpoint(0);
    }

    public enum ELEVATOR_STATE {
        Saftey(SAFE_HEIGHT, SAFE_ANGLE),
        L1(L_1_HEIGHT, L1_ANGLE),
        L2(L_2_HEIGHT, L2_ANGLE),
        L3(L_3_HEIGHT, L3_ANGLE),
        L4(L_4_HEIGHT, L4_ANGLE),
        INTAKE(INTAKE_HEIGHT, INTAKE_ANGLE),
        HOME(HOME_HEIGHT, HOME_ANGLE);

        private final int elevatorDistance;
        private final double elevatorAngle;

        ELEVATOR_STATE(int elevatorDistance, double elevatorAngle) {
            this.elevatorDistance = elevatorDistance;
            this.elevatorAngle = elevatorAngle;
        }

        public int getElevatorDistance() {
            return elevatorDistance;
        }

        public double getElevatorAngle() {
            return elevatorAngle;
        }

    }

    // Manual control for elevator and intake rotation
    public void setManualSpeed(double input) {
        this.leftMotor.setVoltage(input * 12);
        this.rightMotor.setVoltage(input * 12);

        this.outputElevatorVolts.set(input * 12);
    }

    public void setIntakeAngleSpeed(double speed) {
        this.intakeAngleMotor.set(speed);
    }

    // setting setpoints and PID methods to run 
    public void setDesiredElevatorState() {
        this.desiredElevatorHeightConfirmed = this.desiredElevatorHeight;
        this.elevatorPIDController.setSetpoint(desiredElevatorHeightConfirmed);
    }

    public void setDesiredIntakeState(ELEVATOR_STATE desriedIntakeState) {
        this.desiredElevatorAngle = desriedIntakeState.getElevatorAngle();
        this.desiredElevatorHeight = desriedIntakeState.getElevatorDistance();

        this.intakeAnglePIDController.setSetpoint(desiredElevatorAngle);
    }

    public void goToDesiredHeight() {
        var motorVoltage = this.elevatorPIDController.calculate(
                this.getCurrentElevatorDistance(),
                this.desiredElevatorHeightConfirmed
        );

        if(motorVoltage >12) {
            this.outputElevatorVolts.set(12);
            this.leftMotor.setVoltage(12);
            this.rightMotor.setVoltage(12);
        } else {
            this.outputElevatorVolts.set(motorVoltage);
            this.leftMotor.setVoltage(motorVoltage);
            this.rightMotor.setVoltage(motorVoltage);
        }
    }

    public void goToDesiredAngle() {
        var motorVoltage = this.intakeAnglePIDController.calculate(
                this.getCurrentIntakeAngle(),
                this.desiredElevatorAngle
        );

        this.intakeVolts.set(motorVoltage);

        this.intakeAngleMotor.setVoltage(-motorVoltage);
    }

    // Checking encoder values
    public boolean intakeRotationAtSetpoint() {
        return intakeAnglePIDController.atSetpoint();
    }

    public boolean elevatorHeightAtSetpoint() {
        return elevatorPIDController.atSetpoint();
    }

    public int getCurrentElevatorDistance() {return this.barroonEncoder.get();}

    public double getCurrentIntakeAngle() {return this.intakeAngleMotor.getAlternateEncoder().getPosition();}

    // set voltage to zero for end commands and saftey
    public void setIntakeRotationVolatgeToZero() {
        this.intakeAngleMotor.setVoltage(0);
    }

    public void setElevatorVolatgeToZero() {
        this.leftMotor.setVoltage(0);
        this.rightMotor.setVoltage(0);
    }

    public double getDesiredElevatorState() {
        return elevatorPIDController.getSetpoint();
    }

    // reset encoders
    public void resetElevatorEncoder() {
        this.barroonEncoder.reset();
    }

    public void resetIntakeRotationEncoder() {
        this.intakeAngleMotor.getAlternateEncoder().setPosition(0);
    }

    public double driveAccelerationLimitation() {
        driveAccelerationProportionalLimitationMultiplier = 
            (elevatorAccelerationCalculation *
            (Math.abs(getCurrentElevatorDistance()) + 1)) //+ 1 so that it != zero
            + 1;
        return driveAccelerationProportionalLimitationMultiplier;
    }

    @Override
    public void periodic() {
        this.desiredHeightPub.set(this.desiredElevatorHeight);
        this.desiredAnglePub.set(this.desiredElevatorAngle);

        this.actualElevatorHeightPub.set(this.getCurrentElevatorDistance());
        this.actualElevatorAnglePub.set(this.getCurrentIntakeAngle());

        this.intakeRotationError.set(this.intakeAnglePIDController.getError());
        this.intakePIDSetpoint.set(this.intakeAnglePIDController.getSetpoint());
        this.elevatorPIDSetpoint.set(this.elevatorPIDController.getSetpoint()); 
        

        //this.driveAccelerationProportionalLimitation(); //we always want this to be updating

        this.elevatorLeftCurrent.set(this.leftMotor.getOutputCurrent());

        this.getCurrentElevatorDistance();
        this.getCurrentIntakeAngle();
    }

    
}