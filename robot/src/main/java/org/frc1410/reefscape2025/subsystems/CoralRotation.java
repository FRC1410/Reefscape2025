package org.frc1410.reefscape2025.subsystems;

import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.reefscape2025.util.NetworkTables;

import com.revrobotics.spark.*;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.AlternateEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import static org.frc1410.reefscape2025.util.IDs.CORAL_ROTATION_MOTOR;
import static org.frc1410.reefscape2025.util.Tuning.*;

public class CoralRotation implements TickedSubsystem{

    private final SparkMax coralRotationMotor;

    private double desiredAngle = 0;

    private final PIDController coralPIDController = new PIDController(
            CORAL_ANGLE_P,
            CORAL_ANGLE_I,
            CORAL_ANGLE_D
    );

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Coral Rotation");

    private final DoublePublisher coralRotationError = NetworkTables.PublisherFactory(this.table, "Coral Rotation Error", 0);
    private final DoublePublisher coralPIDSetpointPub = NetworkTables.PublisherFactory(this.table, "Coral PID Setpoint", 0);
    private final DoublePublisher desiredCoralAnglePub = NetworkTables.PublisherFactory(this.table, "Desired Coral Angle", 0);
    private final DoublePublisher actualCoralAnglePub = NetworkTables.PublisherFactory(this.table, "Actual Coral Angle", 0);
    private final DoublePublisher coralVolts = NetworkTables.PublisherFactory(this.table, "Coral Volts", 0);


    public CoralRotation() {
        this.coralRotationMotor = new SparkMax(CORAL_ROTATION_MOTOR, SparkLowLevel.MotorType.kBrushless);

        var coralRotationMotorConfig = new SparkMaxConfig();

        coralRotationMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        coralRotationMotorConfig.smartCurrentLimit(30);

        coralRotationMotorConfig.inverted(true);

        var alternateEncoderConfig = new AlternateEncoderConfig();
        alternateEncoderConfig.inverted(true);
        coralRotationMotorConfig.alternateEncoder.apply(alternateEncoderConfig);

        this.coralRotationMotor.configure(
            coralRotationMotorConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters
        );
        
        this.coralRotationMotor.getAlternateEncoder().setPosition(0.0);

        this.coralPIDController.setTolerance(CORAL_ANGLE_TOLERANCE);


        this.coralPIDController.setSetpoint(ElevatorStates.HOME.getDesiredCoralAngle());
    }

    public double getCurrentCoralAngle() {
        return this.coralRotationMotor.getAlternateEncoder().getPosition();
    }

    public double getCoralSetPoint() {
        return this.coralPIDController.getSetpoint();
    }

    public void setCoralSetPoint() {
        this.coralPIDController.setSetpoint(0);
    }


    public void setDesiredLevelAngle(ElevatorStates desiredAngleState) {
        
        this.desiredAngle = desiredAngleState.getDesiredCoralAngle();
        this.coralPIDController.setSetpoint(desiredAngle);
    }


    public void goToDesiredAngle() {
        var motorVoltage = this.coralPIDController.calculate(
                this.getCurrentCoralAngle(),
                this.desiredAngle
        );
        this.coralVolts.set(motorVoltage);
        this.coralRotationMotor.setVoltage(motorVoltage);
    }

    public void setCoralRotationVoltageToZero() {
        this.coralRotationMotor.setVoltage(0);
    }

    public void setCoralAngleManually(double value) {
        this.coralPIDController.setSetpoint(this.getCurrentCoralAngle() - value);
    }

    public void resetAngleEncoder() {
        this.coralRotationMotor.getAlternateEncoder().setPosition(0);
    }

    public boolean atSetpoint() {
        return coralPIDController.atSetpoint();
    }



    @Override
    public void periodic() {
        this.coralPIDSetpointPub.set(this.coralPIDController.getSetpoint());
        this.actualCoralAnglePub.set(this.getCurrentCoralAngle());


        this.goToDesiredAngle();
    }
    
}
