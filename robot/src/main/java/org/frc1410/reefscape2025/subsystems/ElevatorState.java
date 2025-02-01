package org.frc1410.reefscape2025.subsystems;

import java.util.HashMap;
import static org.frc1410.reefscape2025.util.Constants.*;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class ElevatorState implements Subsystem{

    public int State(String state) {
        HashMap<String, Integer> elevatorstates = new HashMap<String, Integer>();
        elevatorstates.put("HOME", ELEVATOR_HOME_ENCODER_VALUE);
        elevatorstates.put("LEVEL_1", ELEVATOR_L1_ENCODER_VALUE);
        elevatorstates.put("LEVEL_2", ELEVATOR_L2_ENCODER_VALUE);
        elevatorstates.put("LEVEL_3", ELEVATOR_L3_ENCODER_VALUE);
        elevatorstates.put("LEVEL_4", ELEVATOR_L4_ENCODER_VALUE);

        return elevatorstates.get(state);
    }

    public int Rotate(String state) {
        HashMap<String, Integer> intakeStates = new HashMap<String, Integer>();
        intakeStates.put("HOME", INTAKE_HOME_ENCODER_VALUE);
        intakeStates.put("LEVEL_ONE_THROUGH_THREE", INTAKE_L1L3_ENCODER_VALUE);
        intakeStates.put("LEVEL_4", INTAKE_L4_ENCODER_VALUE);
        intakeStates.put("INTAKE", INTAKE_INTAKING_ENCODER_VALUE);

        return intakeStates.get(state);
    }
}




