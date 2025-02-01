package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Tuning.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public class Elevator implements TickedSubsystem {

    private final TalonFX leftMotor;
    private final TalonFX rightMotor;
    private final SparkMax intakeAngleMotor = new SparkMax(INTAKE_ROTATION_MOTOR, SparkLowLevel.MotorType.kBrushless);
    private final Encoder intakeAngleEncoder = new Encoder(INTAKE_ANGLE_ENCODER_CHANNEL_A, INTAKE_ANGLE_ENCODER_CHANNEL_B, true);
    private final Encoder barroonEncoder = new Encoder(ELEVATOR_HEIGHT_ENCODER_CHANNEL_A, ELEVATOR_HEIGHT_ENCODER_CHANNEL_B, true);

    private final PIDController elevatorController = new PIDController(LEFT_ELEVATOR_MOTOR_P, LEFT_ELEVATOR_MOTOR_I, LEFT_ELEVATOR_MOTOR_D);

    private int currentElevatorState;
    private int desiredElevatorState = 0;

    private int currentIntakeRotation;
    private int desiredIntakeRotation = 0;



    public Elevator() {


        this.leftMotor = new TalonFX(LEFT_ELEVATOR_MOTOR);
        var leftMotorConfig = new TalonFXConfiguration();
        leftMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        leftMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        leftMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        leftMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        leftMotorConfig.Slot0.kP = LEFT_ELEVATOR_MOTOR_P;
        leftMotorConfig.Slot0.kI = LEFT_ELEVATOR_MOTOR_I;
        leftMotorConfig.Slot0.kD = LEFT_ELEVATOR_MOTOR_D;
        
        this.leftMotor.getConfigurator().apply(leftMotorConfig);

        this.rightMotor = new TalonFX(RIGHT_ELEVATOR_MOTOR);

        var rightMotorConfig = new TalonFXConfiguration();
        rightMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        rightMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        rightMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        rightMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        rightMotorConfig.Slot0.kP = RIGHT_ELEVATOR_MOTOR_P;
        rightMotorConfig.Slot0.kI = RIGHT_ELEVATOR_MOTOR_I;
        rightMotorConfig.Slot0.kD = RIGHT_ELEVATOR_MOTOR_D;
        this.rightMotor.getConfigurator().apply(rightMotorConfig);
    }

    public void resetPosition(double baroonBalue) {
        
    }

    public int reciveDesiredElevatorState() {
        return currentElevatorState;
    }

    public void configureDesiredElevatorState(int desiredHeight) {
        this.desiredElevatorState = desiredHeight;
    }

    public Integer getDesiredElevatorState() {
        return desiredElevatorState;
    }

    public void configureDesiredIntakeRotation(int desiredRotation) {
        this.desiredIntakeRotation = desiredRotation;
    }

    public int getDesiredIntakeRotation() {
        return this.desiredIntakeRotation;
    }

    public void elevatorSpeed(double speed) {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }

    public void intakeSpeed(double speed) {
        intakeAngleMotor.set(speed);
    }

    @Override
    public void periodic() {
        currentElevatorState = barroonEncoder.get();
    }


}