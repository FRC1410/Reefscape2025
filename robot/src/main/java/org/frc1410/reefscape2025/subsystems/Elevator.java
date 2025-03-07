package org.frc1410.reefscape2025.subsystems;

import static org.frc1410.reefscape2025.util.IDs.ELEVATOR_HEIGHT_ENCODER_CHANNEL_A;
import static org.frc1410.reefscape2025.util.IDs.ELEVATOR_HEIGHT_ENCODER_CHANNEL_B;
import static org.frc1410.reefscape2025.util.IDs.LEFT_ELEVATOR_MOTOR;
import static org.frc1410.reefscape2025.util.IDs.RIGHT_ELEVATOR_MOTOR;

import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

import static org.frc1410.reefscape2025.util.Constants.*;
import static org.frc1410.reefscape2025.util.Tuning.*;

import java.util.function.DoubleSupplier;


public class Elevator implements TickedSubsystem {
    private final SparkMax leftMotor;
    private final SparkMax rightMotor;
   
    private final Encoder elevatorEncoder;

    private int desiredElevatorHeight = 1;

    private final PIDController elevatorPIDController = new PIDController(
            ELEVEATOR_P,
            ELEVEATOR_I,
            ELEVEATOR_D
    );

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

        this.elevatorEncoder = new Encoder(ELEVATOR_HEIGHT_ENCODER_CHANNEL_A, ELEVATOR_HEIGHT_ENCODER_CHANNEL_B, false, CounterBase.EncodingType.k4X);
        this.elevatorEncoder.reset();

        this.elevatorPIDController.setTolerance(ELEVATOR_TOLERANCE);
    }

    public int getElevatorSetpoint() {return (int) this.elevatorPIDController.getSetpoint();}

    public int getCurrentElevatorDistance() {return this.elevatorEncoder.get();}

    public void setDesiredElevatorState(ElevatorStates desiredElevatorStateHeight) {
        this.desiredElevatorHeight = desiredElevatorStateHeight.getDesiredElevatorHeight();
        this.elevatorPIDController.setSetpoint(desiredElevatorHeight);
    }

    public void goToDesiredHeight() {
        var motorVoltage = this.elevatorPIDController.calculate(
                this.getCurrentElevatorDistance(),
                this.desiredElevatorHeight
        );

        if(motorVoltage >12) {
            this.leftMotor.setVoltage(12);
            this.rightMotor.setVoltage(12);
        } else {
            this.leftMotor.setVoltage(motorVoltage);
            this.rightMotor.setVoltage(motorVoltage);
        }
    }

    public boolean atSetpoint() {
        return elevatorPIDController.atSetpoint();
    }

    public void setElevatorVolatgeToZero() {
        this.leftMotor.setVoltage(0);
        this.rightMotor.setVoltage(0);
    }

    public void resetEncoder() {
        this.elevatorEncoder.reset();
    }

    public double driveSpeedLimit() {
        limiter = 
            (elevatorAccelerationCalculation *
            (Math.abs(getCurrentElevatorDistance()) + 1)) //+ 1 so that it != zero
            + 1;
        return limiter;
    }



    @Override
    public void periodic() {
        driveSpeedLimit();
        this.goToDesiredHeight();
    }
    



}