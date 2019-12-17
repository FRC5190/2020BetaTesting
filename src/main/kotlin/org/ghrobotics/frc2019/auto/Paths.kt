/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto

import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.geometry.Translation2d
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.mathematics.twodim.trajectory.FalconTrajectoryConfig
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.operations.div
import org.ghrobotics.lib.mathematics.units.seconds

/**
 * Container for autonomous paths.
 */
object Paths {
    // Trajectory constraints
    private val kMaxVelocity = 7.feet / 1.seconds
    private val kMaxAcceleration = 4.feet / 1.seconds / 1.seconds

    // Trajectory config
    private val config = FalconTrajectoryConfig(kMaxVelocity, kMaxAcceleration)
        .setKinematics(Drivetrain.kinematics)
        .addConstraint(CentripetalAccelerationConstraint(2.0 /* meters per second squared */))

    // An example trajectory to follow. All units in meters.
    var trajectory: Trajectory =
        TrajectoryGenerator.generateTrajectory(
            Pose2d(), // Pass through these two interior waypoints, making an 's' curve path
            listOf(
                Translation2d(1.0, 1.0),
                Translation2d(2.0, -1.0)
            ), // End 3 meters straight ahead of where we started, facing forward
            Pose2d(3.0, 0.0, Rotation2d()), // Pass config
            config
        )
}
