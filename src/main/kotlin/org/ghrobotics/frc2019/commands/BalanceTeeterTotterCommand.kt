/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import kotlin.math.sin
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.LinearVelocity
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.operations.div
import org.ghrobotics.lib.mathematics.units.operations.times
import org.ghrobotics.lib.mathematics.units.seconds

class BalanceTeeterTotterCommand : FalconCommand(Drivetrain) {
    override fun execute() {
        val pitchError = Drivetrain.getPitch()
        val output = kP * pitchError.sin
        Drivetrain.setOutput(output, output, SIUnit(0.0), SIUnit(0.0))
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.setNeutral()
    }

    override fun isFinished(): Boolean {
        return Drivetrain.getPitch().degrees < 0.5
    }

    companion object {
        // We want to go at 1 feet per second output when the error is 20 degrees.
        val kP: SIUnit<LinearVelocity> = (1 / sin(Math.toRadians(20.0))) * 0.7.feet / 1.seconds
    }
}
