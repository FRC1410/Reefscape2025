package org.frc1410.reefscape2025.subsystems;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.util.Optional;

import static org.frc1410.reefscape2025.util.Constants.*;

public class Camera implements Subsystem {
    private final PhotonCamera photonCamera;
    private final PhotonPoseEstimator photonPoseEstimator;

    public Camera(String cameraName, Transform3d cameraPose) {
         photonCamera = new PhotonCamera(cameraName);

        photonPoseEstimator = new PhotonPoseEstimator(
                APRIL_TAG_FIELD_LAYOUT,
                PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
                cameraPose
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

    public double getAmbiguity() {
        //TODO: there is a bug where the code will crash because the best target is null
        double poseAmbeguity = 1;
        if(this.photonCamera.getLatestResult().hasTargets()) {
            if(photonCamera.getLatestResult().getBestTarget() != null) {
                poseAmbeguity = photonCamera.getLatestResult().getBestTarget().poseAmbiguity;
            }
        } else {
            poseAmbeguity = 1;
        }
        return poseAmbeguity;
    }
}
