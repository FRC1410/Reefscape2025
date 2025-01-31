package org.frc1410.reefscape2025.subsytems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.reefscape2025.util.Constants.*;
import static org.frc1410.reefscape2025.util.IDs.*;

public class ClimberMotor implements Subsystem {

    //Motor init and config

    private final SparkMax climberMotorOne = new SparkMax(CLIMBER_MOTOR_ONE, SparkLowLevel.MotorType.kBrushless);

    private final SparkMax climberMotorTwo = new SparkMax(CLIMBER_MOTOR_TWO, SparkLowLevel.MotorType.kBrushless);

    public ClimberMotor() {
        var climberMotorConfigOne = new SparkMaxConfig();

        climberMotorConfigOne.smartCurrentLimit(40);
        climberMotorConfigOne.idleMode(SparkBaseConfig.IdleMode.kBrake);
        climberMotorConfigOne.inverted(CLIMBER_MOTOR_ONE_IS_INVERTED);
        this.climberMotorOne.configure(climberMotorConfigOne, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);


        var climberMotorConfigTwo = new SparkMaxConfig();

        climberMotorConfigTwo.smartCurrentLimit(40);
        climberMotorConfigTwo.idleMode(SparkBaseConfig.IdleMode.kBrake);
        climberMotorConfigTwo.inverted(CLIMBER_MOTOR_TWO_IS_INVERTED);
        this.climberMotorTwo.configure(climberMotorConfigTwo, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    //Motor run

    public void RunClimberMotor(double speed) {
        this.climberMotorOne.set(speed);
        this.climberMotorTwo.set(speed);
    }

    //Limit Switch

    DigitalInput climbLimitSwitch = new DigitalInput(CLIMB_LIMIT_SWITCH);

    //Lim switch call

    public boolean GetClimbLimitSwitch() {
        return(climbLimitSwitch.get());
    }

}
