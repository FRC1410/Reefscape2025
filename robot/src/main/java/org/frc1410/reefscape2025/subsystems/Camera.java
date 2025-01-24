package org.frc1410.reefscape2025.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import java.util.Optional;

import static org.frc1410.reefscape2025.util.Constants.*;

public class Camera implements Subsystem {
    private final PhotonCamera photonCamera = new PhotonCamera(REEF_CAMERA);
    private final PhotonPoseEstimator photonPoseEstimator;

    public Camera() {
        photonPoseEstimator = new PhotonPoseEstimator(
                APRIL_TAG_FIELD_LAYOUT,
                PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
                CAMERA_POSE
        );
    }

    public Optional<EstimatedRobotPose> getEstimatedPose() {
        if(this.photonCamera.getAllUnreadResults().getFirst().hasTargets()) {
            var pose = this.photonPoseEstimator.update(this.photonCamera.getAllUnreadResults().getFirst());
            return pose;
        } else {
            return Optional.empty();
        }
    }
}
