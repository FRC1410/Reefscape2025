package org.frc1410.reefscape2025.subsytems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.IDs.CLIMBER_MOTOR;
import static org.frc1410.reefscape2025.util.Constants.CLIMBER_MOTOR_IS_INVERTED;
import static org.frc1410.reefscape2025.util.IDs.CLIMB_LIMIT_SWITCH;

public class ClimberMotor implements Subsystem {
    private final SparkMax climberMotor = new SparkMax(CLIMBER_MOTOR, SparkLowLevel.MotorType.kBrushless);

    DigitalInput climbLimitSwitch = new DigitalInput(CLIMB_LIMIT_SWITCH);

    public boolean GetClimbLimitSwitch() {
        return(climbLimitSwitch.get());
    }

    public ClimberMotor() {
        var climberMotorConfig = new SparkMaxConfig();

        climberMotorConfig.smartCurrentLimit(40);
        climberMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        climberMotorConfig.inverted(CLIMBER_MOTOR_IS_INVERTED);
        this.climberMotor.configure(climberMotorConfig, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    public void RunClimberMotor(double speed) {
        this.climberMotor.set(speed);
    }

}
