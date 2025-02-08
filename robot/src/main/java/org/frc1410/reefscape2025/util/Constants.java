package org.frc1410.reefscape2025.util;

import edu.wpi.first.units.Unit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;

public final class Constants {
    //99 TO 1 gear ratio of the intake angle motor
    public static boolean INTAKE_ANGLE_ROTATION_MOTOR_INVERTED = false;

    public static Distance ENCODER_SHAFT_RADIUS = Units.Inches.of(0.8755);
    public static Distance ENCODER_SHAFT_CIRCUMFERENCE = Units.Inches.of(ENCODER_SHAFT_RADIUS.times(2 * Math.PI).in(Units.Inches));

    public static int L_1_HEIGHT = 1790;
    public static int L_2_HEIGHT = 3500;
    public static int L_3_HEIGHT = 6118;
    public static int L_4_HEIGHT = 10680;
    public static int INTAKE_HEIGHT = 0;
    public static int HOME_HEIGHT = 0;

    public static double L1_ANGLE = 0.595;
    public static double L2_ANGLE = -0.553;
    public static double L3_ANGLE = -0.553;
    public static double L4_ANGLE = 0.674;
    public static double INTAKE_ANGLE = 0;
    public static double HOME_ANGLE = 0;

    //L'Bozo
    public static final boolean LBOZO_FRONT_MOTOR_IS_INVERTED = false;
    public static final boolean LBOZO_BACK_MOTOR_IS_INVERTED = true;

    //Climb
    public static final double CLIMBER_MOTOR_MAX_SPEED = 1.0;
    public static final double CLIMBER_MOTOR_INVERTED_MAX_SPEED = -1.0;
    public static final boolean CLIMBER_MOTOR_ONE_IS_INVERTED = false;
    public static final boolean CLIMBER_MOTOR_TWO_IS_INVERTED = true;

    public static final double LED_BRIGHTNESS = 1.0;
}