package org.frc1410.reefscape2025.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.IDs.*;

public class Elevator implements Subsystem {

    private final TalonSRX leftMotor;
    private final TalonSRX rightMotor;

    public Elevator() {
        leftMotor = new TalonSRX(LEFT_ELEVATOR_MOTOR);
        var leftMotorConfig = new TalonFXConfiguration();
        leftMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;
        leftMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;

        leftMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        rightMotor = new TalonSRX(RIGHT_ELEVATOR_MOTOR);
    }
}
