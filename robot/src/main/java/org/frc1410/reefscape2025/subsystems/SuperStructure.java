package org.frc1410.reefscape2025.subsystems;
import static org.frc1410.reefscape2025.util.Constants.*;

public enum SuperStructure {
    HOME(HOME_HEIGHT, HOME_ANGLE),
    INTAKE(INTAKE_HEIGHT, INTAKE_ANGLE),
    L1(L_1_HEIGHT, L1_ANGLE),
    L2(L_2_HEIGHT, L2_ANGLE),
    L3(L_3_HEIGHT, L3_ANGLE),
    L4(L_4_HEIGHT, L4_ANGLE);


    private int height;
    private double angle;


    SuperStructure(int height, double angle) {
        this.height = height;
        this.angle = angle;
    }

    public int desiredElevatorHeight() {
        return this.height;
    }

    public double desiredAngle() {
        return this.angle;
    }
    
}
