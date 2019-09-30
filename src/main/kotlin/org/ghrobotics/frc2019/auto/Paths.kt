/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto

import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint
import org.ghrobotics.lib.mathematics.twodim.geometry.Pose2d
import org.ghrobotics.lib.mathematics.twodim.trajectory.FalconTrajectoryGenerator
import org.ghrobotics.lib.mathematics.units.derived.acceleration
import org.ghrobotics.lib.mathematics.units.derived.velocity
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.meters

/**
 * Container for autonomous paths.
 */
object Paths {
    val trajectory: Trajectory = FalconTrajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(5.326.feet, 9.697.feet, Rotation2d.fromDegrees(0.0)),
            Pose2d(18.651.feet, 7.997.feet, Rotation2d.fromDegrees(-23.0)),
            Pose2d(22.13.feet, 2.612.feet, Rotation2d.fromDegrees(-148.0))
        ),
        listOf(CentripetalAccelerationConstraint(2.1336)),
        0.0.meters.velocity,
        0.0.meters.velocity,
        8.feet.velocity,
        3.0.feet.acceleration,
        false
    )
}
