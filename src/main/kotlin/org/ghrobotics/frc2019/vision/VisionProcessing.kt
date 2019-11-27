/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.vision

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.geometry.Transform2d
import kotlin.math.tan
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.twodim.geometry.Transform2d
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.seconds

/**
 * Object that manages Vision processing.
 */
object VisionProcessing : FalconSubsystem() {

    private val frontCamera = ChameleonCamera("Front-Camera")
    private val frontCameraAngle = Rotation2d.fromDegrees(15.0)
    private val frontCameraHeight = 13.inches

    private val targetHeight = 19.inches

    // Adds samples to the TargetTracker periodically.
    override fun periodic() {
        // Check whether there are any targets at all.
        if (!frontCamera.isValid) {
            return
        } else {
            // Calculate when the frame was captured.
            val captureTime = Timer.getFPGATimestamp().seconds - frontCamera.latency

            // Get the pitch and yaw values from the camera.
            val (pitch, yaw) = frontCamera.pitch to frontCamera.yaw

            // Calculate the distance to the target.
            val distance = (targetHeight - frontCameraHeight) / tan(frontCameraAngle.radians + pitch.radians)

            // Create the camera to target transform. We don't care about the rotation of the
            // target relative to the camera so we will just set that to zero. We also need
            // to add the transform between the robot center and the camera.
            val transform = Constants.kFrontCameraRelativeToRobot +
                Transform2d(distance * yaw.cos, distance * yaw.sin, Rotation2d())

            // We will get the robot pose at the frame capture time.
            val pose = Drivetrain.getPose(captureTime)

            // Add the target to TargetTracker.
            TargetTracker2020.addSample(captureTime, pose + transform.toTransform())
        }
    }
}

fun Pose2d.toTransform(): Transform2d {
    return Transform2d(translation, rotation)
}
