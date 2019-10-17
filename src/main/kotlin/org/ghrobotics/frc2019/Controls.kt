/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Solenoid
import org.ghrobotics.frc2019.commands.ToggleFingersCommand
import org.ghrobotics.frc2019.commands.VisionDriveCommand
import org.ghrobotics.lib.wrappers.hid.*

object Controls {

    val solenoid = Solenoid(41, 2)
    var set = false

    val driverController = xboxController(0) {
        // Toggle the state of the fingers when the A button is pressed.
        triggerAxisButton(GenericHID.Hand.kRight).changeOn {
            println("test")
            ToggleFingersCommand().schedule()
        }

        button(kB).changeOn {
            solenoid.set(!set)
            set = !set
        }


        // Vision align when Y is pressed.
        triggerAxisButton(GenericHID.Hand.kLeft).change(VisionDriveCommand())
    }

    fun update() {
        driverController.update()
    }
}
