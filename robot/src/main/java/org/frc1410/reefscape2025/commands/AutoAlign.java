package org.frc1410.reefscape2025.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.util.ReefSides;

import java.util.Optional;
import static org.frc1410.reefscape2025.util.Constants.*;

public class AutoAlign extends Command {
    private final Drivetrain drivetrain;

    private ReefSides closestReefSide;
    private boolean isRight;
    private PathPlannerPath path;

    private Command findThenFollowPath = null;

    public AutoAlign(Drivetrain drivetrain, boolean isRight) {
        this.drivetrain = drivetrain;
        this.isRight = isRight;
    }

    @Override
    public void initialize() {
        var reefList = DriverStation.getAlliance().equals(Optional.of(DriverStation.Alliance.Blue))
                ? BLUE_REEF
                : RED_REEF;

        Pose2d currentRobotPose = this.drivetrain.getEstimatedPosition();

        ReefSides nearestSide = null;
        double smallestDistance = Double.MAX_VALUE;

        for(ReefSides reefSide : reefList) {
            var distanceFromRobot = reefSide.pose().getTranslation().getDistance(currentRobotPose.getTranslation());
            if(distanceFromRobot < smallestDistance) {
                smallestDistance = distanceFromRobot;
                nearestSide = reefSide;
            }
        }

        this.closestReefSide = nearestSide;

        if(isRight) {
            path = this.closestReefSide.rightPath();
        } else {
            path = this.closestReefSide.leftPath();
        }

        System.out.println("Closest Reef: " + this.closestReefSide + " selected path: " + path);

        this.findThenFollowPath = AutoBuilder.pathfindThenFollowPath(
                path,
                PATH_FINDING_CONSTRAINTS
        );

        this.findThenFollowPath.initialize();
    }

    @Override
    public void execute() {
        if(this.findThenFollowPath != null) {
            this.findThenFollowPath.execute();
        } else {
            System.out.println("Path is null. Failed to execute");
        }
    }

    @Override
    public boolean isFinished() {
        return findThenFollowPath.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        if(this.findThenFollowPath != null) {
            this.findThenFollowPath.end(interrupted);
        }
    }


}
