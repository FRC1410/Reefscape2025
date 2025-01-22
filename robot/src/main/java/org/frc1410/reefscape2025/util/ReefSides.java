package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;

public record ReefSides(PathPlannerPath leftPath, PathPlannerPath rightPath, Pose2d pose) {}
