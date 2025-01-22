package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.path.PathPlannerPath;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PathLoader {
    private final String pathName;
    PathPlannerPath path;

    public PathLoader(String pathName) {
        this.pathName = pathName;
    }

    public PathPlannerPath getPath() {
        try {
            path = PathPlannerPath.fromPathFile(pathName);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
