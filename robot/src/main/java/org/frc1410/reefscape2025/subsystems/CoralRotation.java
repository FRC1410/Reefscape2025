package org.frc1410.reefscape2025.subsystems;

import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.reefscape2025.util.NetworkTables;

import com.revrobotics.servohub.ServoHub.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.AlternateEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import static org.frc1410.reefscape2025.util.IDs.CORAL_ROTATION_MOTOR;
import static org.frc1410.reefscape2025.util.Tuning.*;

public class CoralRotation implements TickedSubsystem{

    private final SparkMax coralRotationMotor;
    private double desiredAngle;

    private final PIDController coralRotationPIDController = new PIDController(
        CORAL_ROTATION_P, 
        CORAL_ROTATION_I, 
        CORAL_ROTATION_D
    );

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Coral Rotation");
    private final DoublePublisher desiredAnglePublisher = NetworkTables.PublisherFactory(this.table, "Desired Angle", 0);
    private final DoublePublisher actualAnglePublisher = NetworkTables.PublisherFactory(this.table, "Actual Angle", 0);
    private final DoublePublisher coralRotationVoltsPublisher = NetworkTables.PublisherFactory(this.table, "Coral Rotation Volts", 0);





    public CoralRotation() {
        this.coralRotationMotor = new SparkMax(CORAL_ROTATION_MOTOR, MotorType.kBrushless);

        var coralRotationConfig = new SparkMaxConfig();

        coralRotationConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        coralRotationConfig.smartCurrentLimit(30);
        coralRotationConfig.inverted(true);

        var alternateEncoderConfig = new AlternateEncoderConfig();
        alternateEncoderConfig.inverted(true);
        coralRotationConfig.alternateEncoder.apply(alternateEncoderConfig);

        this.coralRotationMotor.configure(
            coralRotationConfig, 
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters
        );

        coralRotationMotor.getAlternateEncoder().setPosition(0.0);

        coralRotationPIDController.setTolerance(CORAL_ROTATION_TOLERANCE);
        this.desiredAngle = 0.0;
    }

    public double getCurrentCoralAngle() {
        return this.coralRotationMotor.getAlternateEncoder().getPosition();
    }

    public void setDesiredCoralRotation(SuperStructure angles) {
        this.desiredAngle = angles.desiredAngle();
        this.coralRotationPIDController.setSetpoint(desiredAngle);
    }

    public void goToAngle() {
        var motorVoltage = this.coralRotationPIDController.calculate(
            getCurrentCoralAngle(),
            desiredAngle
        );

        this.coralRotationVoltsPublisher.set(motorVoltage);

        this.coralRotationMotor.setVoltage(motorVoltage);
    }

    public boolean coralRotationAtSetpoint() {
        return Math.abs(desiredAngle - getCurrentCoralAngle()) < 0.1;
    }


    public void setPositionManual(double change) {
        this.desiredAngle = this.getCurrentCoralAngle() + change;
        this.coralRotationPIDController.setSetpoint(this.desiredAngle);
    }

    public void resetRotationEncoder() {
        this.coralRotationMotor.getAlternateEncoder().setPosition(0.0);
    }



    @Override
    public void periodic() {
        this.desiredAnglePublisher.set(desiredAngle);
        this.actualAnglePublisher.set(this.getCurrentCoralAngle());
        
    }
}
