package org.frc1410.reefscape2025;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.reefscape2025.commands.DriveLooped;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.util.NetworkTables;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.frc1410.reefscape2025.util.IDs.*;
import static org.frc1410.reefscape2025.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {
	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = this.nt.getTable("Auto");

	private final Controller driverController = new Controller(this.scheduler, DRIVER_CONTROLLER, 0.1);
	private final Controller operatorController = new Controller(this.scheduler, OPERATOR_CONTROLLER, 0.1);

	// Subsystems
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain(this.subsystems));

	RobotConfig robotConfig = RobotConfig.fromGUISettings();

	public Robot() throws IOException, ParseException {
		AutoBuilder.configure(
				this.drivetrain::getEstimatedPosition,
				this.drivetrain::resetPose,
				this.drivetrain::getChassisSpeeds,
                this.drivetrain::drive,
				HOLONOMIC_AUTO_CONFIG,
				robotConfig,
				() -> {
					var alliance = DriverStation.getAlliance();

					if(alliance.isPresent()) {
						return alliance.get() == DriverStation.Alliance.Red;
					}
					return false;
				},
				drivetrain
		);
	}

	private final AutoSelector autoSelector = new AutoSelector()
			.add("0", () -> new InstantCommand())
			.add("1", () -> new PathPlannerAuto("1 coral"));

	{
		var profiles = new String[this.autoSelector.getProfiles().size()];
		for (var i = 0; i < profiles.length; i++) {
			profiles[i] = this.autoSelector.getProfiles().get(i).name();
		}

		var autoChoicesPub = NetworkTables.PublisherFactory(this.table, "Choices", profiles);
		autoChoicesPub.accept(profiles);
	}

		@Override
		public void autonomousSequence () {}

		@Override
		public void teleopSequence () {
		this.scheduler.scheduleDefaultCommand(new DriveLooped(
				this.drivetrain,
				this.driverController.LEFT_Y_AXIS,
				this.driverController.LEFT_X_AXIS,
				this.driverController.RIGHT_X_AXIS,
				this.driverController.LEFT_TRIGGER
		), TaskPersistence.EPHEMERAL);

			this.driverController.Y.whenPressed(new InstantCommand(
					() -> {
						if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
							this.drivetrain.setYaw(Rotation2d.fromDegrees(180));
						} else {
							this.drivetrain.setYaw(Rotation2d.fromDegrees(0));
						}
					}
			), TaskPersistence.GAMEPLAY);
	}


		@Override
		public void testSequence () {}

		@Override
		protected void disabledSequence () {}
}
