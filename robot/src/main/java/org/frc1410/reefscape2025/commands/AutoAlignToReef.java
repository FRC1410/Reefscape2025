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
import static org.frc1410.reefscape2025.util.Constants.*;

import java.io.IOException;
import java.util.Optional;


public class AutoAlignToReef extends Command {
    private final Drivetrain drivetrain;

    private Command pathFollowingCommand = null;
    private PathPlannerPath path = null;

    boolean end = false;


    public AutoAlignToReef(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements();
    }







    @Override
    public void initialize() {
        var scorePositions = DriverStation.getAlliance().equals(Optional.of(DriverStation.Alliance.Blue)) ? SCORING_POSITIONS_BLUE : SCORING_POSITIONS_RED;

        ScoringPath nearestPath = null;
        double smallestDistance = Double.MAX_VALUE;

//        for(ScoringPath position : scorePositions) {
//            var distance = position.
//        }


        
        try {
            path = PathPlannerPath.fromPathFile("AlignPath1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(path == null) {
            this.end = true;
            System.out.println(" ");
            System.out.println("NO PATH FOUND");
            System.out.println(" ");
        }
    

        //TODO ADD MULTIPLE SCORING POSITIONS


        PathConstraints pathConstraints = new PathConstraints(
                3.0, //MAX VELOCITY
                2.5,  //MAX ACCELERATION
                Units.degreesToRadians(360), //MAX ROTATIONAL VELOCITY (RADIANS)
                Units.degreesToRadians(360) //MAX ROTATIONAL ACCELERATION (RADIANS)
        );


        this.pathFollowingCommand = AutoBuilder.pathfindThenFollowPath(
                path,
                pathConstraints
        );
      
        pathFollowingCommand.initialize();
    }



    @Override
    public void execute() {
        if(this.pathFollowingCommand != null) {
            if(!this.pathFollowingCommand.isFinished()) {
                this.pathFollowingCommand.execute();
            }
        } else {
            System.out.println("pathFollowingCommand is NULL");
        }
       
    }

    @Override
    public boolean isFinished() {
        return end;
        //TODO Make return true when we get to desired pos / score coral
    }


    @Override
    public void end(boolean interrupted) {
        if(this.pathFollowingCommand != null) {
            pathFollowingCommand.end(interrupted);
        }
    }
}
