package org.frc1410.reefscape2025.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public class LBozo implements Subsystem {

    private final SparkMax lBozoFrontMotor = new SparkMax(LBOZO_FRONT_MOTOR, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax lBozoBackMotor = new SparkMax(LBOZO_BACK_MOTOR, SparkLowLevel.MotorType.kBrushless);

    public LBozo() {
        var FrontMotorConfig = new SparkMaxConfig();

        FrontMotorConfig.smartCurrentLimit(30);
        FrontMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        FrontMotorConfig.inverted(LBOZO_FRONT_MOTOR_IS_INVERTED);
        this.lBozoFrontMotor.configure(FrontMotorConfig, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);



        var BackMotorConfig = new SparkMaxConfig();

        BackMotorConfig.smartCurrentLimit(30);
        BackMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        BackMotorConfig.inverted(LBOZO_BACK_MOTOR_IS_INVERTED);
        this.lBozoBackMotor.configure(FrontMotorConfig, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    public void RunLBozo(double speed) {
        this.lBozoFrontMotor.set(speed);
        this.lBozoBackMotor.set(speed);
    }

}
