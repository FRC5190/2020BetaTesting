/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto.routines

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import org.ghrobotics.lib.commands.parallel

/**
 * Represents a runnable autonomous routine.
 */
interface AutoRoutine {
    /**
     * The name of the routine.
     */
    val name: String

    /**
     * The command or command group that represents the routine.
     */
    val routine: Command

    /**
     * Builds the autonomous routine, with debug features.
     * @return The command group representing the complete routine.
     */
    fun build() = parallel {
        +PrintCommand("Starting routine: $name")
        +routine
    }
}
