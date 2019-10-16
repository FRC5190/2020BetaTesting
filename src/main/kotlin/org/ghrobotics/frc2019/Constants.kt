/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019

import edu.wpi.first.wpilibj.geometry.Rotation2d
import org.ghrobotics.lib.mathematics.twodim.geometry.Pose2d
import org.ghrobotics.lib.mathematics.units.derived.volts
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits
import org.ghrobotics.lib.mathematics.units.operations.div
import org.ghrobotics.lib.mathematics.units.seconds

@Suppress("MemberVisibilityCanBePrivate")
object Constants {

    const val kPCMId = 41

    val kCameraRelativeToCenter = Pose2d(13.inches, (-10).inches, Rotation2d())

    // Drivetrain
    object Drivetrain {
        // IDs
        const val kLeftMasterId = 1
        const val kLeftSlave1Id = 2
        const val kRightMasterId = 3
        const val kRightSlave1Id = 4

        // Native Unit Model
        val kWheelRadius = 3.inches
        val kGearRatio = 7.29.nativeUnits
        val kNativeUnitModel = NativeUnitLengthModel(kGearRatio, kWheelRadius)

        // Ramsete Tracker
        const val kBeta = 2.0
        const val kZeta = 0.7

        // Characterization
        val kLeftKv = 1.75.volts / (1.meters / 1.seconds)
        val kLeftKa = 0.0398.volts / (1.meters / 1.seconds / 1.seconds)
        val kLeftKs = 0.204.volts
        val kRightKv = 1.76.volts / (1.meters / 1.seconds)
        val kRightKa = 0.0601.volts / (1.meters / 1.seconds / 1.seconds)
        val kRightKs = 0.191.volts

        // Track Width
        val kTrackWidth = 28.75.inches

        // Closed Loop Gains
        const val kP = 0.0000
    }

    object Intake {
        const val kFingersId = 0
    }

    object Vision {
        const val kVisionCameraFPS = 30.0
        val kCameraTimeout = 2.seconds
        val kTargetTrackingDistanceErrorTolerance = 16.inches
        val kTargetTrackingMinLifetime = 0.1.seconds
        val kTargetTrackingMaxLifetime = 0.5.seconds
    }
}
