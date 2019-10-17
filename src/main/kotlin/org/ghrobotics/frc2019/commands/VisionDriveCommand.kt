/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.frc2019.vision.TargetTracker
import kotlin.math.abs

class VisionDriveCommand : TeleopDriveCommand() {

    private var lastKnownTargetPose: Pose2d? = null
    private var prevError = 0.0

    override fun execute() {
        val newTarget = TargetTracker.getBestTarget(true)
        val newPose = newTarget?.averagedPose2d
        if (newTarget?.isAlive == true && newPose != null) {
            lastKnownTargetPose = newPose
        }
        val lastKnownTargetPose = this.lastKnownTargetPose

        if (lastKnownTargetPose == null) {
            super.execute()
        } else {
            val transform = lastKnownTargetPose.relativeTo(Drivetrain.robotPosition)
            val angle = Rotation2d(transform.translation.x, transform.translation.y)

            if (abs(angle.degrees) > 45) {
                this.lastKnownTargetPose = null
            }

            val error = angle.radians
            val turn = kCorrectionKp * error + kCorrectionKd * (error - prevError)
            val linear = -xSource() * 0.3

            prevError = error
            Drivetrain.setPercent(linear - turn, linear + turn)
        }
    }

    override fun end(interrupted: Boolean) {
        prevError = 0.0
        this.lastKnownTargetPose = null
    }

    companion object {
        const val kCorrectionKp = 0.5
        const val kCorrectionKd = 0.0
    }
}
