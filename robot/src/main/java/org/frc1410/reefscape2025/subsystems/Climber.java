package org.frc1410.reefscape2025.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.Constants.*;
import static org.frc1410.reefscape2025.util.IDs.*;

public class Climber implements Subsystem {

    private final SparkMax topClimbMotor = new SparkMax(TOP_CLIMB_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax bottomClimbMotor = new SparkMax(BOTTOM_CLIMB_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

    private final DigitalInput climbLimitSwitch = new DigitalInput(CLIMB_LIMIT_SWITCH);

    public Climber() {
        var topClimbMotorConfig = new SparkMaxConfig();

        topClimbMotorConfig.smartCurrentLimit(40);
        topClimbMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        topClimbMotorConfig.inverted(CLIMBER_MOTOR_ONE_IS_INVERTED);
        this.topClimbMotor.configure(
                topClimbMotorConfig,
                SparkBase.ResetMode.kNoResetSafeParameters,
                SparkBase.PersistMode.kNoPersistParameters
        );

        var bottomClimbMotorConfig = new SparkMaxConfig();

        bottomClimbMotorConfig.smartCurrentLimit(40);
        bottomClimbMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        bottomClimbMotorConfig.inverted(CLIMBER_MOTOR_TWO_IS_INVERTED);
        this.bottomClimbMotor.configure(
                bottomClimbMotorConfig,
                SparkBase.ResetMode.kNoResetSafeParameters,
                SparkBase.PersistMode.kNoPersistParameters
        );
    }

    public void setClimberSpeed(double speed) {
        this.topClimbMotor.set(speed);
        this.bottomClimbMotor.set(speed);
    }

    public boolean getLimitSwitch() {
        return(climbLimitSwitch.get());
    }

}
