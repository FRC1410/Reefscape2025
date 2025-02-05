package org.frc1410.reefscape2025.util;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;

public final class Constants {
    //99 TO 1 gear ratio of the intake angle motor
    public static boolean INTAKE_ANGLE_ROTATION_MOTOR_INVERTED = false;

    public static Distance ENCODER_SHAFT_RADIUS = Units.Inch.of(0.25);
    public static Distance ENCODER_SHAFT_CIRCUMFERENCE = ENCODER_SHAFT_RADIUS.times(2 * Math.PI);

    public static double ELEVATOR_GEAR_RATIO = 1.0;
    public static double INTAKE_ANGLE_GEAR_RATIO = 2.75;

    public static Distance L_1_HEIGHT = Units.Meter.of(1.0);
    public static Distance L_2_HEIGHT = Units.Meter.of(1.0);
    public static Distance L_3_HEIGHT = Units.Meter.of(1.0);
    public static Distance L_4_HEIGHT = Units.Meter.of(1.0);
    public static Distance INTAKE_HEIGHT = Units.Meter.of(1.0);
    public static Distance HOME_HEIGHT = Units.Meter.of(1.0);

    public static Angle L1_ANGLE = Units.Degrees.of(1.0);
    public static Angle L2_ANGLE = Units.Degrees.of(1.0);
    public static Angle L3_ANGLE = Units.Degrees.of(1.0);
    public static Angle L4_ANGLE = Units.Degrees.of(1.0);
    public static Angle INTAKE_ANGLE = Units.Degrees.of(1.0);
    public static Angle HOME_ANGLE = Units.Degrees.of(1.0);

    //L'Bozo
    public static final boolean LBOZO_FRONT_MOTOR_IS_INVERTED = false;
    public static final boolean LBOZO_BACK_MOTOR_IS_INVERTED = true;

    //Climb
    public static final double CLIMBER_MOTOR_MAX_SPEED = 1.0;
    public static final double CLIMBER_MOTOR_INVERTED_MAX_SPEED = -1.0;
    public static final boolean CLIMBER_MOTOR_ONE_IS_INVERTED = false;
    public static final boolean CLIMBER_MOTOR_TWO_IS_INVERTED = false;

    public static final double LED_BRIGHTNESS = 1.0;
}