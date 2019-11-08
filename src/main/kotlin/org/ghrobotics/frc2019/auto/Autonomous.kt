/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto

import edu.wpi.first.wpilibj2.command.CommandGroupBase
import org.ghrobotics.frc2019.auto.routines.BalanceTeeterTotterRoutine
import org.ghrobotics.frc2019.auto.routines.CharacterizationRoutine
import org.ghrobotics.frc2019.auto.routines.TrackTrajectoryRoutine
import org.ghrobotics.lib.commands.S3ND

/**
 * Manages the autonomous period of the game, along with other autonomous
 * configuration tasks.
 */
object Autonomous {
    // Empty string to make the meme work.
    private const val it = ""

    /**
     * Starts the autonomous routine.
     * @param mode The autonomous mode to run.
     */
    fun start(mode: Mode) {
        // Get the command representing the routine.
        val just: CommandGroupBase = when (mode) {
            Mode.CHARACTERIZE -> CharacterizationRoutine
            Mode.TRACK_TRAJECTORY -> TrackTrajectoryRoutine()
            Mode.TEETER_TOTTER -> BalanceTeeterTotterRoutine()
        }.build()

        // Start the routine.
        just S3ND it
    }

    /**
     * Represents the auto mode.
     */
    enum class Mode {
        CHARACTERIZE, TRACK_TRAJECTORY, TEETER_TOTTER
    }
}
