package org.frc1410.reefscape2025.subsystems;

import static org.frc1410.reefscape2025.util.Constants.*;

public enum ElevatorStates {
        HOME(HOME_HEIGHT, HOME_ANGLE, 0),
        L1(L_1_HEIGHT, L1_ANGLE, 0),
        L2(L_2_HEIGHT, L2_ANGLE, 0),
        L3(L_3_HEIGHT, L3_ANGLE, 0),
        L4(L_4_HEIGHT, L4_ANGLE, 0),
        INTAKE(INTAKE_HEIGHT, INTAKE_ANGLE, 0);

        private final int height;
        private final double angle;
    
        ElevatorStates(int height, double angle, double OuttakeSpeed) {
            this.height = height;
            this.angle = angle;
        } 

        public int getDesiredElevatorHeight() {
            return height;
        }

        public double getDesiredCoralAngle() {
            return angle;
        }
}
