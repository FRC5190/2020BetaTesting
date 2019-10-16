/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.subsystems

import edu.wpi.first.wpilibj.Solenoid
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.lib.commands.FalconSubsystem

object Intake : FalconSubsystem() {

    // Solenoid for fingers
    private val fingers = Solenoid(Constants.kPCMId, Constants.Intake.kFingersId)

    // State of the fingers
    var areFingersOpen: Boolean = false
        private set

    /**
     * Closes the fingers to hold on to the hatch panel.
     */
    fun closeFingers() {
        fingers.set(false)
        areFingersOpen = false
    }

    /**
     * Opens the fingers to pick up a hatch panel.
     */
    fun openFingers() {
        fingers.set(true)
        areFingersOpen = true
    }
}
