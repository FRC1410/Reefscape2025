package org.frc1410.reefscape2025.util;

import edu.wpi.first.units.Unit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;

public final class Constants {
    public static int SAFE_HEIGHT = 1;
    public static int L_1_HEIGHT = 1790;
    public static int L_2_HEIGHT = 3500;
    public static int L_3_HEIGHT = 6118;
    public static int L_4_HEIGHT = 10680;
    public static int INTAKE_HEIGHT = 0;
    public static int HOME_HEIGHT = 0;  

    public static double SAFE_ANGLE = 0;
    public static double L1_ANGLE = 0.595;
    public static double L2_ANGLE = 0.553;
    public static double L3_ANGLE = 0.553;
    public static double L4_ANGLE = 0.674;
    public static double INTAKE_ANGLE = 1.808;
    public static double HOME_ANGLE = 0;

    public static final double slopeCalculationDriveAcceleration = -1/(L_4_HEIGHT/.8);

    //L'Bozo
    public static final boolean LBOZO_FRONT_MOTOR_IS_INVERTED = false;
    public static final boolean LBOZO_BACK_MOTOR_IS_INVERTED = true;

    //Climb
    public static final double CLIMBER_MOTOR_MAX_SPEED = 1;
    public static final double CLIMBER_MOTOR_INVERTED_MAX_SPEED = -1;
    public static final boolean CLIMBER_MOTOR_ONE_IS_INVERTED = false;
    public static final boolean CLIMBER_MOTOR_TWO_IS_INVERTED = false;

    //LED's
    public static final double LED_BRIGHTNESS = 1.0;

    //Drivetrain
    public static double driveAccelerationProportionalLimitationMultiplier = 0;
}