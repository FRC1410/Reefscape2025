package org.frc1410.reefscape2025.subsystems;

import java.util.HashMap;

public class ElevatorState {

    public int State(String keyLimePie) {
        HashMap<String, Integer> elevatorstates = new HashMap<String, Integer>();
        elevatorstates.put("Num-Num", 1);
        elevatorstates.put("BARRON", 1);
        elevatorstates.put("BARROON", 1);
        elevatorstates.put("BARROOON", 1);
        elevatorstates.put("BARROOOON", 1);

        return elevatorstates.get(keyLimePie);
    }
}




