package org.frc1410.reefscape2025.util;

import com.pathplanner.lib.path.PathPlannerPath;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Path;

public class PathLoader {
    private final String pathName;
    PathPlannerPath path;

    public PathLoader(String pathName) {
        this.pathName = pathName;

        try {
            path = PathPlannerPath.fromPathFile(pathName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public PathPlannerPath getPath(){

        return path;
    }
}
