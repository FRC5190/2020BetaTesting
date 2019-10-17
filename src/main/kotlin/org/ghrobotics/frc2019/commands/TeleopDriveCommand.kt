/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.commands

import edu.wpi.first.wpilibj.GenericHID
import org.ghrobotics.frc2019.Controls
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.wrappers.hid.*

open class TeleopDriveCommand : FalconCommand(Drivetrain) {
    override fun execute() {
        Drivetrain.curvatureDrive(-xSource() * 0.3, cSource() * 0.5, qSource())
    }

    companion object {
        val xSource = Controls.driverController.getY(GenericHID.Hand.kLeft)
        val cSource = Controls.driverController.getX(GenericHID.Hand.kLeft)
        val qSource = Controls.driverController.getRawButton(kX)
    }
}
