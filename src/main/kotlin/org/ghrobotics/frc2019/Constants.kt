/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019

import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits

@Suppress("MemberVisibilityCanBePrivate")
object Constants {

    // Drivetrain
    object Drivetrain {
        // IDs
        const val kLeftMasterId = 1
        const val kLeftSlave1Id = 2
        const val kRightMasterId = 3
        const val kRightSlave1Id = 4
        const val kPigeonId = 17

        // Native Unit Model
        val kWheelRadius = 3.inches
        val kGearRatio = 7.29.nativeUnits
        val kNativeUnitModel = NativeUnitLengthModel(kGearRatio, kWheelRadius)

        // Ramsete Tracker
        const val kBeta = 2.0
        const val kZeta = 0.7

        // Track Width
        val kTrackWidth = 28.75.inches

        // Closed Loop Gains
        const val kP = 0.0009
    }
}
