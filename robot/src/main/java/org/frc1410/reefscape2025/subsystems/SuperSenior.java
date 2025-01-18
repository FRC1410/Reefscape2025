package org.frc1410.reefscape2025.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.IDs.*;

public class SuperSenior implements Subsystem {
    private final SparkMax hugLeftMotor = new SparkMax(HUG_LEFT_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax hugRightMotor = new SparkMax(HUG_RIGHT_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

    public SuperSenior() {
        var hugLeftConfig = new SparkMaxConfig();
        var hugRightConfig = new SparkMaxConfig();

        hugLeftConfig.smartCurrentLimit(30);
        hugRightConfig.smartCurrentLimit(30);

        hugLeftConfig.inverted(true);
        hugRightConfig.inverted(true);

        hugLeftMotor.configure(hugLeftConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
        hugRightMotor.configure(hugRightConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    public void setLeftHugSpeed(double Speed) {
        hugLeftMotor.set(Speed);
    }

    public void setRightHugSpeed(double Speed) {
        hugRightMotor.set(Speed);
    }


}
