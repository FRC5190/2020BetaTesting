/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.frc2019.vision.TargetTracker2020

/**
 * Command that aligns with a vision target. The linear velocity is
 * still controller by the driver but the angular velocity uses
 * the target location relative to the robot.
 */
class VisionDriveCommand : TeleopDriveCommand() {

    override fun execute() {
        // Get the drivetrain position.
        val robotPose = Drivetrain.getPose()

        // Get the best target.
        val target = TargetTracker2020.getBestTarget(robotPose)

        // If the target doesn't exist, continue driving.
        if (target == null) {
            super.execute()
        } else {
            // Use vision alignment here.
            // Get the robot to target transform.
            val transform = target.averagePose - robotPose

            Drivetrain.arcadeDrive(-xSource(), kP * transform.rotation.radians)
        }
    }

    companion object {
        const val kP = 0.3
    }
}
