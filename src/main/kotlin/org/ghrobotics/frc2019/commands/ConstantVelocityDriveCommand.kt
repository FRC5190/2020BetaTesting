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
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.LinearVelocity

class ConstantVelocityDriveCommand(private val speed: SIUnit<LinearVelocity>) : FalconCommand(Drivetrain) {

    override fun execute() {
        Drivetrain.setOutput(speed, speed, SIUnit(0.0), SIUnit(0.0))
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.setNeutral()
    }
}
