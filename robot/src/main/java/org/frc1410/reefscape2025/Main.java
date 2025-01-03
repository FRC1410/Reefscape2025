package org.frc1410.reefscape2025;

import edu.wpi.first.wpilibj.RobotBase;

public interface Main {
  static void main(String[] args) {
	  RobotBase.startRobot(Robot::new);
  }
}
