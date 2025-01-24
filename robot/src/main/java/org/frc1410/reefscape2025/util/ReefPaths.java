package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ReefPaths {

    public static final ReefSides BLUE_NORTH_REEF = new ReefSides(
            new PathLoader("North Left").getPath(),
            new PathLoader("North Right").getPath(),
            new Pose2d(6.2, 4, Rotation2d.fromDegrees(180))
    );

    public static final ReefSides BLUE_NORTH_EAST = new ReefSides(
            new PathLoader("North East Left").getPath(),
            new PathLoader("North East Right").getPath(),
            new Pose2d(5.250, 2.775, Rotation2d.fromDegrees(120))
    );

    public static final ReefSides BLUE_SOUTH_EAST = new ReefSides(
            new PathLoader("South East Left").getPath(),
            new PathLoader("South East Right").getPath(),
            new Pose2d(3.715, 2.835, Rotation2d.fromDegrees(60))
    );

    public static final ReefSides BLUE_SOUTH_REEF = new ReefSides(
            new PathLoader("South Left").getPath(),
            new PathLoader("South Right").getPath(),
            new Pose2d(3, 4, Rotation2d.fromDegrees(0))
    );

    public static final ReefSides BLUE_SOUTH_WEST_REEF = new ReefSides(
            new PathLoader("South West Left").getPath(),
            new PathLoader("South West Right").getPath(),
            new Pose2d(3.750, 5.280, Rotation2d.fromDegrees(-60))
    );

    public static final ReefSides BLUE_NORTH_WEST_REEF = new ReefSides(
            new PathLoader("North West Left").getPath(),
            new PathLoader("North West Right").getPath(),
            new Pose2d(5.245, 5.3, Rotation2d.fromDegrees(-120))
    );
}
