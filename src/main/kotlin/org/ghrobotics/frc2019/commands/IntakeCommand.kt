/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import org.ghrobotics.frc2019.subsystems.Intake
import org.ghrobotics.lib.commands.FalconCommand

/**
 * Opens the intake fingers
 */
class OpenFingersCommand : FalconCommand(Intake) {
    override fun initialize() {
        Intake.openFingers()
    }
}

/**
 * Closes the intake fingers.
 */
class CloseFingersCommand : FalconCommand(Intake) {
    override fun initialize() {
        Intake.closeFingers()
    }
}

/**
 * Toggles the intake fingers
 */
class ToggleFingersCommand : FalconCommand(Intake) {
    override fun initialize() {
        if (Intake.areFingersOpen) Intake.closeFingers() else Intake.openFingers()
    }
}
