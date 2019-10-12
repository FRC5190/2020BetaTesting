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
import edu.wpi.first.wpilibj.geometry.Translation2d
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.debug.LiveDashboard

object TargetTracker : FalconSubsystem() {

    private val targets = mutableSetOf<TrackedTarget>()

    override fun periodic() {
        synchronized(targets) {
            val currentTime = Timer.getFPGATimestamp()

            val currentRobotPose = Drivetrain.robotPosition

            // Update and remove old targets
            targets.removeIf {
                it.update(currentTime, currentRobotPose)
                !it.isAlive
            }
            // Publish to dashboard
            LiveDashboard.visionTargets = targets.asSequence()
                .filter { it.isReal }
                .map { it.averagedPose2d }
                .toList()
        }
    }

    fun addSamples(creationTime: Double, samples: Iterable<Pose2d>) {
        if (creationTime >= Timer.getFPGATimestamp()) return // Cannot predict the future

        synchronized(targets) {
            for (samplePose in samples) {
                val closestTarget = targets.minBy {
                    it.averagedPose2d.translation.getDistance(samplePose.translation)
                }
                val sample = TrackedTargetSample(creationTime, samplePose)
                if (closestTarget == null ||
                    closestTarget.averagedPose2d.translation.getDistance(samplePose.translation)
                    > Constants.Vision.kTargetTrackingDistanceErrorTolerance.value
                ) {
                    // Create new target if no targets are within tolerance
                    targets += TrackedTarget(sample)
                } else {
                    // Add sample to target within tolerance
                    closestTarget.addSample(sample)
                }
            }
        }
    }

    fun getBestTarget(isFrontTarget: Boolean) = synchronized(targets) {
        targets.asSequence()
            .filter {
                if (!it.isReal) return@filter false
                val x = it.averagedPose2dRelativeToBot.translation.x
                if (isFrontTarget) x >= 0.0 else x <= 0.0
            }.minBy { it.averagedPose2dRelativeToBot.translation.norm }
    }

    fun getBestTargetUsingReference(referencePose: Pose2d, isFrontTarget: Boolean) = synchronized(targets) {
        targets.asSequence()
            .associateWith { it.averagedPose2d.relativeTo(referencePose) }
            .filter {
                val x = it.value.translation.x
                it.key.isReal && if (isFrontTarget) x > 0.0 else x < 0.0
            }
            .minBy { it.value.translation.norm }?.key
    }

    fun getAbsoluteTarget(translation2d: Translation2d) = synchronized(targets) {
        targets.asSequence()
            .filter {
                it.isReal &&
                    translation2d.getDistance(it.averagedPose2d.translation) <=
                    Constants.Vision.kTargetTrackingDistanceErrorTolerance.value
            }
            .minBy { it.averagedPose2d.translation.getDistance(translation2d) }
    }

    class TrackedTarget(
        initialTargetSample: TrackedTargetSample
    ) {

        private val samples = mutableSetOf<TrackedTargetSample>()

        /**
         * The averaged pose2d for x time
         */
        var averagedPose2d = initialTargetSample.targetPose
            private set

        var averagedPose2dRelativeToBot = Pose2d()
            private set

        /**
         * Targets will be "alive" when it has at least one data point for x time
         */
        var isAlive = true
            private set

        /**
         * Target will become a "real" target once it has received data points for x time
         */
        var isReal = false
            private set

        var stability = 0.0
            private set

        init {
            addSample(initialTargetSample)
        }

        fun addSample(newSamples: TrackedTargetSample) = synchronized(samples) {
            samples.add(newSamples)
        }

        fun update(currentTime: Double, currentRobotPose: Pose2d) = synchronized(samples) {
            // Remove expired samples
            samples.removeIf { currentTime - it.creationTime >= Constants.Vision.kTargetTrackingMaxLifetime.value }
            // Update State
            isAlive = samples.isNotEmpty()
            if (samples.size >= 2) isReal = true
            stability =
                (samples.size / (Constants.Vision.kVisionCameraFPS * Constants.Vision.kTargetTrackingMaxLifetime.value))
                    .coerceAtMost(1.0)
            // Update Averaged Pose
            var accumulatedX = 0.0
            var accumulatedY = 0.0
            var accumulatedAngle = 0.0
            for (sample in samples) {
                accumulatedX += sample.targetPose.translation.x
                accumulatedY += sample.targetPose.translation.y
                accumulatedAngle += sample.targetPose.rotation.radians
            }
            averagedPose2d = Pose2d(
                accumulatedX / samples.size,
                accumulatedY / samples.size,
                Rotation2d(accumulatedAngle / samples.size)
            )
            averagedPose2dRelativeToBot = averagedPose2d.relativeTo(currentRobotPose)
        }
    }

    data class TrackedTargetSample(
        val creationTime: Double,
        val targetPose: Pose2d
    )
}
