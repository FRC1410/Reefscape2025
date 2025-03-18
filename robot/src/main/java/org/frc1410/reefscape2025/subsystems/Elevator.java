package org.frc1410.reefscape2025.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.AlternateEncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.reefscape2025.util.Constants;
import org.frc1410.reefscape2025.util.NetworkTables;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Tuning.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public class Elevator implements TickedSubsystem {
    private final SparkMax leftMotor;
    private final SparkMax rightMotor;

    private final Encoder barroonEncoder;

    private final PIDController elevatorPIDController = new PIDController(
            ELEVEATOR_P,
            ELEVEATOR_I,
            ELEVEATOR_D
    );

    
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Elevator");

    private final DoublePublisher desiredHeightPub = NetworkTables.PublisherFactory(this.table, "Desired Height", 0);
    private final DoublePublisher actualElevatorHeightPub = NetworkTables.PublisherFactory(this.table, "Actual Elevator Height", 0);
    private final DoublePublisher elevatorPIDSetpoint = NetworkTables.PublisherFactory(this.table, "Elevator PID Setpoint", 0);
    private final DoublePublisher elevatorRightCurrent = NetworkTables.PublisherFactory(this.table, "Right Elevator Current", 0);
    private final DoublePublisher elevatorLeftCurrent = NetworkTables.PublisherFactory(this.table, "Left Elevator Current", 0);
    private final DoublePublisher actualRightElevatorVolts = NetworkTables.PublisherFactory(this.table, "Actual Right Elevator Volts", 0);
    private final DoublePublisher actualLeftElevatorVolts = NetworkTables.PublisherFactory(this.table, "Actual Left Elevator Volts", 0);
    private final DoublePublisher outputElevatorVolts = NetworkTables.PublisherFactory(this.table, "Desired Elevator Volts", 0);
    

    private int desiredElevatorHeight = 0;

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
        

        this.barroonEncoder = new Encoder(ELEVATOR_HEIGHT_ENCODER_CHANNEL_A, ELEVATOR_HEIGHT_ENCODER_CHANNEL_B, false, CounterBase.EncodingType.k4X);
        this.barroonEncoder.reset();

        this.elevatorPIDController.setTolerance(ELEVATOR_TOLERANCE);

        elevatorPIDController.setSetpoint(0);
    }


    // Manual control for elevator and intake rotation
    public void setManualSpeed(double input) {
        this.leftMotor.setVoltage(input * 12);
        this.rightMotor.setVoltage(input * 12);

        this.outputElevatorVolts.set(input * 12);
    }


    // setting setpoints and PID methods to run 
    public void setDesiredElevatorState(SuperStructure height) {
        this.desiredElevatorHeight = height.desiredElevatorHeight();
        this.elevatorPIDController.setSetpoint(desiredElevatorHeight);
    }


    public void goToDesiredHeight() {
        var motorVoltage = this.elevatorPIDController.calculate(
                this.getCurrentElevatorDistance(),
                this.desiredElevatorHeight
        );

        if(motorVoltage >12) {
            this.outputElevatorVolts.set(12);
            this.leftMotor.setVoltage(12);
            this.rightMotor.setVoltage(12);
        } else {
            this.outputElevatorVolts.set(motorVoltage);
            this.leftMotor.setVoltage(motorVoltage);
            this.rightMotor.setVoltage(motorVoltage);
        }
    }



    public boolean elevatorHeightAtSetpoint() {
        return Math.abs(this.getDesiredElevatorDistance() - this.getCurrentElevatorDistance()) < 250;
    }

    public int getCurrentElevatorDistance() {return this.barroonEncoder.get();}
    public int getDesiredElevatorDistance() {return this.desiredElevatorHeight;}

    public void setElevatorVolatgeToZero() {
        this.leftMotor.setVoltage(0);
        this.rightMotor.setVoltage(0);
    }

    public double getDesiredElevatorState() {
        return elevatorPIDController.getSetpoint();
    }

    // reset encoders
    public void resetElevatorEncoder() {
        this.barroonEncoder.reset();
    }

    public double driveAccelerationLimitation() {
        driveAccelerationProportionalLimitationMultiplier = 
            (elevatorAccelerationCalculation *
            (Math.abs(getCurrentElevatorDistance()) + 1)) //+ 1 so that it != zero
            + 1;
        return driveAccelerationProportionalLimitationMultiplier;
    }

    @Override
    public void periodic() {
        this.desiredHeightPub.set(this.desiredElevatorHeight);
        this.actualElevatorHeightPub.set(this.getCurrentElevatorDistance());
        this.elevatorPIDSetpoint.set(this.elevatorPIDController.getSetpoint()); 
        

        //this.driveAccelerationProportionalLimitation(); //we always want this to be updating

        this.elevatorLeftCurrent.set(this.leftMotor.getOutputCurrent());

        this.getCurrentElevatorDistance();
    }
}