package org.frc1410.reefscape2025.commands;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.pathfinding.Pathfinding;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.reefscape2025.subsystems.Drivetrain;
import org.frc1410.reefscape2025.util.ScoringPath;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Optional;

import static org.frc1410.reefscape2025.util.Constants.HOLONOMIC_AUTO_CONFIG;

public class AutoAlignToPoint extends Command{
   private final Drivetrain drivetrain;

   private RobotConfig robotConfig;

   private Command goToPoint = null;

   

   public AutoAlignToPoint(Drivetrain drivetrain) {
      this.drivetrain = drivetrain;

      this.addRequirements(drivetrain);
   }

   @Override
   public void initialize() {
      Pose2d targetpose = new Pose2d(10, 15, Rotation2d.fromDegrees(180));

      PathConstraints pathConstraints = new PathConstraints(
                 3.0, //MAX VELOCITY
                 2.5,  //MAX ACCELERATION
                 Units.degreesToRadians(360), //MAX ROTATIONAL VELOCITY (RADIANS)
                 Units.degreesToRadians(360) //MAX ROTATIONAL ACCELERATION (RADIANS)
         );
      
      this.goToPoint = AutoBuilder.pathfindToPose(
              targetpose,
              pathConstraints,
              0
         );

      this.goToPoint.initialize();
   }

   @Override
   public void execute() {
      if(this.goToPoint != null) {

         this.goToPoint.execute();
      } else {
         System.out.println(" ");
         System.out.println("COMMAND IS NULL");
         System.out.println(" ");
      }
      
   }

   @Override
   public boolean isFinished() {
      return false;
   }

   @Override
   public void end(boolean interrupted) {
      if(this.goToPoint != null) {
         this.goToPoint.end(interrupted);
      }
   }
}