/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto.routines

import org.ghrobotics.frc2019.auto.Paths
import org.ghrobotics.frc2019.subsystems.Drivetrain

/**
 * Routine that tests trajectory tracking.
 */
class TrackTrajectoryRoutine : AutoRoutine {
    override val name = "Track Trajectory"
    override val routine = Drivetrain.followTrajectory(Paths.trajectory)
}
