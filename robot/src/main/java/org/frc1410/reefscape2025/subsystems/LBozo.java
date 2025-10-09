package org.frc1410.reefscape2025.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.reefscape2025.util.NetworkTables;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public class LBozo implements Subsystem {

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("L'Bozo (Intake/Outtake)");

    private final DoublePublisher limSwitchPublisher = NetworkTables.PublisherFactory(this.table, "Limit Switch state", 0);

    public final DigitalInput limitSwitch = new DigitalInput(LBOZO_LIMIT_SWITCH);

    private final SparkMax lBozoTopMotor = new SparkMax(LBOZO_TOP_MOTOR, SparkLowLevel.MotorType.kBrushless);
//    private final SparkMax lBozoBottomMotor = new SparkMax(LBOZO_BOTTOM_MOTOR, SparkLowLevel.MotorType.kBrushless);
    private final SparkMax outtakeMotor = new SparkMax(LBOZO_OUTTAKE, SparkLowLevel.MotorType.kBrushless);

    public LBozo() {
        var topMotorConfig = new SparkMaxConfig();

        topMotorConfig.smartCurrentLimit(30);
        topMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        topMotorConfig.inverted(LBOZO_FRONT_MOTOR_IS_INVERTED);
        this.lBozoTopMotor.configure(topMotorConfig, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);

        var bottomMotorConfig = new SparkMaxConfig();

        bottomMotorConfig.smartCurrentLimit(30);
        bottomMotorConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);
        bottomMotorConfig.inverted(LBOZO_BACK_MOTOR_IS_INVERTED);
//        this.lBozoBottomMotor.configure(bottomMotorConfig, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
        var other = new SparkMaxConfig();

        other.smartCurrentLimit(30);
        other.idleMode(SparkBaseConfig.IdleMode.kBrake);
        other.inverted(true);
        this.outtakeMotor.configure(other, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    public void setLBozoSpeed(double speed) {
        this.lBozoTopMotor.set(speed);
//        this.lBozoBottomMotor.set(speed)
    }

    public void setOuttakeSpeed(double speed) {
        this.outtakeMotor.set(speed);
    }

    public boolean hasCoral(){
        return !this.limitSwitch.get();
    }

    @Override
    public void periodic() {
        if (!this.limitSwitch.get()) {
            this.limSwitchPublisher.set(1);
        } else {
            this.limSwitchPublisher.set(0);
        }
    }
}
