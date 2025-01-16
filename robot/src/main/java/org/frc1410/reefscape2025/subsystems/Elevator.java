package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Tuning.*;

public class Elevator implements Subsystem {

    private final TalonFX leftMotor;
    private final TalonFX rightMotor;

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
}
