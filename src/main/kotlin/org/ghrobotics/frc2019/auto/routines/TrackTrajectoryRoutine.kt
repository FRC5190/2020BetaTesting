/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto.routines

import edu.wpi.first.wpilibj2.command.InstantCommand
import org.ghrobotics.frc2019.auto.Paths
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.sequential

/**
 * Routine that tests trajectory tracking.
 */
class TrackTrajectoryRoutine : AutoRoutine {
    override val name = "Track Trajectory"
    override val routine = sequential {
        +InstantCommand(Runnable {
            Drivetrain.resetPosition(Paths.trajectory.sample(0.0).poseMeters)
        }, Drivetrain)
        +Drivetrain.followTrajectory(Paths.trajectory)
    }
}
