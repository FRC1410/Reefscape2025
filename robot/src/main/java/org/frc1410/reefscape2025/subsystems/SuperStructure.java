package org.frc1410.reefscape2025.subsystems;
import static org.frc1410.reefscape2025.util.Constants.*;

public enum SuperStructure {
    HOME(HOME_HEIGHT, HOME_ANGLE, 1),
    INTAKE(INTAKE_HEIGHT, INTAKE_ANGLE, 2),
    L1(L_1_HEIGHT, L1_ANGLE, 3),
    L2(L_2_HEIGHT, L2_ANGLE, 4),
    L3(L_3_HEIGHT, L3_ANGLE, 5),
    L4(L_4_HEIGHT, L4_ANGLE, 6);


    private int height;
    private double angle;
    private int lit;


    SuperStructure(int height, double angle, int lit) {
        this.height = height;
        this.angle = angle;
        this.lit = lit;
    }

    public int desiredElevatorHeight() {
        return this.height;
    }

    public double desiredAngle() {
        return this.angle;
    }

    public int itLitQuestionmMrk() {
        return this.lit;
    }
    
}
