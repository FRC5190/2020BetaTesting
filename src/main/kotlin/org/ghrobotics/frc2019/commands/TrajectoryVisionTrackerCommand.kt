/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj.trajectory.Trajectory
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.frc2019.vision.TargetTracker
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.debug.LiveDashboard
import org.ghrobotics.lib.mathematics.twodim.geometry.x_u
import org.ghrobotics.lib.mathematics.twodim.geometry.y_u
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.inFeet
import org.ghrobotics.lib.utils.Source

class TrajectoryVisionTrackerCommand(
    private val trajectorySource: Source<Trajectory>,
    private val radius: SIUnit<Meter>
) : FalconCommand(Drivetrain) {
    private var prevError = 0.0

    private var prevLeftVelocity = 0.0
    private var prevRightVelocity = 0.0

    private val timer = Timer()
    private var elapsed = 0.0
    private lateinit var trajectory: Trajectory

    private var lastKnownTargetPose: Pose2d? = null

    /**
     * Initializes the command,
     */
    override fun initialize() {
        trajectory = trajectorySource()
        timer.start()

        prevLeftVelocity = 0.0
        prevRightVelocity = 0.0

        LiveDashboard.isFollowingPath = true
    }

    /**
     * Executes at 50 Hz.
     */
    override fun execute() {
        // Get the elapsed time
        elapsed = timer.get()

        // Get the current trajectory state.
        val currentTrajectoryState = trajectory.sample(elapsed)

        val withinVisionRadius = Drivetrain.robotPosition.translation.getDistance(
            trajectory.sample(trajectory.totalTimeSeconds).poseMeters.translation
        ) < radius.value

        if (withinVisionRadius) {
            val newTarget = TargetTracker.getBestTarget(true)
            val newPose = newTarget?.averagedPose2d
            if (newTarget?.isAlive == true && newPose != null) lastKnownTargetPose = newPose
        }

        val lastKnownTargetPose = this.lastKnownTargetPose

        val chassisSpeeds: ChassisSpeeds
        if (lastKnownTargetPose != null) {
            val transform = lastKnownTargetPose.relativeTo(Drivetrain.robotPosition)
            val angle = Rotation2d(transform.translation.x, transform.translation.y)
            val error = angle.radians

            val turn = kCorrectionKp * error + kCorrectionKd * (error - prevError)
            prevError = error
            chassisSpeeds = ChassisSpeeds(currentTrajectoryState.velocityMetersPerSecond, 0.0, turn)
        } else {
            // Get the adjusted chassis speeds from the controller.
            chassisSpeeds = Drivetrain.controller.calculate(
                Drivetrain.robotPosition,
                currentTrajectoryState
            )
        }

        // Get the wheel speeds from the chassis speeds.
        val wheelSpeeds = Drivetrain.kinematics.toWheelSpeeds(chassisSpeeds)

        // Calculate accelerations
        val leftAcceleration = (wheelSpeeds.leftMetersPerSecond - prevLeftVelocity) * 50
        val rightAcceleration = (wheelSpeeds.rightMetersPerSecond - prevRightVelocity) * 50

        prevLeftVelocity = wheelSpeeds.leftMetersPerSecond
        prevRightVelocity = wheelSpeeds.rightMetersPerSecond

        Drivetrain.setOutput(
            SIUnit(wheelSpeeds.leftMetersPerSecond),
            SIUnit(wheelSpeeds.rightMetersPerSecond),
            SIUnit(leftAcceleration),
            SIUnit(rightAcceleration)
        )

        if (currentTrajectoryState != null) {
            val referencePose = currentTrajectoryState.poseMeters

            // Update Current Path Location on Live Dashboard
            LiveDashboard.pathX = referencePose.translation.x_u.inFeet()
            LiveDashboard.pathY = referencePose.translation.y_u.inFeet()
            LiveDashboard.pathHeading = referencePose.rotation.radians
        }
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.setNeutral()
        prevError = 0.0

        if (interrupted) {
            DriverStation.reportError("Trajectory tracking was interrupted.", false)
        }
    }

    /**
     * Checks if the trajectory has finished executing.
     */
    override fun isFinished() = elapsed > trajectory.totalTimeSeconds

    companion object {
        const val kCorrectionKp = 4.5
        const val kCorrectionKd = 0.0
    }
}
