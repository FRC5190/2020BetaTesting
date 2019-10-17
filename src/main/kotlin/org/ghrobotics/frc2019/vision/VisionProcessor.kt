/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.vision

import com.google.gson.JsonObject
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.geometry.Transform2d
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.mathematics.twodim.geometry.Translation2d
import org.ghrobotics.lib.mathematics.units.inches

object VisionProcessor {
    fun processData(data: VisionData) {
        val robotPose = Drivetrain.robotPosition
        TargetTracker.addSamples(
            data.timestamp,
            data.targets.asSequence().mapNotNull {
                processReflectiveTape(it, Constants.kCameraRelativeToCenter)
            }.map { robotPose + it.minus(Pose2d()) }.toList()
        )
    }

    private fun processReflectiveTape(data: JsonObject, camera: Pose2d): Pose2d? {
        val angle = Rotation2d.fromDegrees(data["angle"].asDouble)
        val rotation = Rotation2d.fromDegrees(-data["rotation"].asDouble) + angle + Rotation2d.fromDegrees(180.0)
        val distance = data["distance"].asDouble.inches

        return camera + Transform2d(Translation2d(distance, angle), rotation)
    }
}
