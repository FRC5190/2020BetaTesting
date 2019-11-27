/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.FalconCommand

class OpenLoopDriveCommand(
    private val left: Double,
    private val right: Double
) : FalconCommand(Drivetrain) {
    override fun initialize() {
        Drivetrain.setPercent(left, right)
    }
}
