package org.frc1410.reefscape2025.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.util.ScoringPath;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Optional;


public class AutoAlignToReef extends Command {
    private final Drivetrain drivetrain;

    private Command pathFollowingCommand = null;
    private PathPlannerPath path = null;


    public AutoAlignToReef(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements();
    }



    @Override
    public void initialize() {
        var scorePositions = DriverStation.getAlliance().equals(Optional.of(DriverStation.Alliance.Blue)); // Scoring Pose red or blue

        Pose2d currentpose = this.drivetrain.getEstimatedPosition();

        ScoringPath nearestPath = null;
        double smallestDistance = Double.MAX_VALUE;

//        for(ScoringPath position : scorePositions) {
//            var distance = position.
//        }


        PathPlannerPath path = null;
        try {
            path = PathPlannerPath.fromPathFile("AlignPath1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //TODO Find out why it needs a try catch

        //TODO ADD MULTIPLE SCORING POSITIONS


        PathConstraints pathConstraints = new PathConstraints(
                3.0, //MAX VELOCITY
                2.5,  //MAX ACCELERATION
                Units.degreesToRadians(360), //MAX ROTATIONAL VELOCITY (RADIANS)
                Units.degreesToRadians(360) //MAX ROTATIONAL ACCELERATION (RADIANS)
        );




        Command pathFollowingCommand = AutoBuilder.pathfindThenFollowPath(
                path,
                pathConstraints
        );

        pathFollowingCommand.initialize();
    }



    @Override
    public void execute() {
        if(!this.pathFollowingCommand.isFinished()) {
            if(this.pathFollowingCommand != null) {
                this.pathFollowingCommand.execute();
            }
        }
    }


    @Override
    public boolean isFinished() {

        return false;
    }


    @Override
    public void end(boolean interrupted) {
        if(this.pathFollowingCommand != null) {
            pathFollowingCommand.end(interrupted);
        }
    }
}
