/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019

import org.ghrobotics.frc2019.commands.ToggleFingersCommand
import org.ghrobotics.frc2019.commands.VisionDriveCommand
import org.ghrobotics.lib.wrappers.hid.button
import org.ghrobotics.lib.wrappers.hid.kA
import org.ghrobotics.lib.wrappers.hid.kY
import org.ghrobotics.lib.wrappers.hid.xboxController

object Controls {
    val driverController = xboxController(0) {
        // Toggle the state of the fingers when the A button is pressed.
        button(kA).changeOn(ToggleFingersCommand())

        // Vision align when Y is pressed.
        button(kY).change(VisionDriveCommand())
    }
}
