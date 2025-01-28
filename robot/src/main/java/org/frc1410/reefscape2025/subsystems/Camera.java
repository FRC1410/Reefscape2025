package org.frc1410.reefscape2025.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.util.Optional;

import static org.frc1410.reefscape2025.util.Constants.*;

public class Camera implements Subsystem {
    private final PhotonCamera photonCamera;
    private final PhotonPoseEstimator photonPoseEstimator;

    public Camera(String cameraName) {
         photonCamera = new PhotonCamera(cameraName);

        photonPoseEstimator = new PhotonPoseEstimator(
                APRIL_TAG_FIELD_LAYOUT,
                PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
                CAMERA_POSE
        );
    }

    public Optional<EstimatedRobotPose> getEstimatedPose() {
        if(this.photonCamera.getLatestResult().hasTargets()) {
            var pose = this.photonPoseEstimator.update(this.photonCamera.getLatestResult());
            return pose;
        } else {
            return Optional.empty();
        }
    }
}
