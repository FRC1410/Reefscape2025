package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;

/**
 * @param leftPath The path that aligns us to the left of the reef
 * @param rightPath The path that aligns us to the right of the reef
 * @param pose The pose where the beginning of BOTH paths start
 */
public record ReefSides(PathPlannerPath leftPath, PathPlannerPath rightPath, Pose2d pose) {}
