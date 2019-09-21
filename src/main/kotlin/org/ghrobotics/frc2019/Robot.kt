/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019

import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint
import org.ghrobotics.frc2019.auto.Autonomous
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.sequential
import org.ghrobotics.lib.mathematics.twodim.geometry.Pose2d
import org.ghrobotics.lib.mathematics.twodim.trajectory.DefaultTrajectoryGenerator
import org.ghrobotics.lib.mathematics.twodim.trajectory.constraints.CentripetalAccelerationConstraint
import org.ghrobotics.lib.mathematics.units.derived.acceleration
import org.ghrobotics.lib.mathematics.units.derived.velocity
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.wrappers.FalconTimedRobot
import org.ghrobotics.lib.wrappers.networktables.enumSendableChooser

object Robot : FalconTimedRobot() {

<<<<<<< HEAD
    // Chooser for the auto mode.
    private val autoModeSelector = enumSendableChooser<Autonomous.Mode>()
=======
    val trajectory = DefaultTrajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(5.326.feet, 9.697.feet, Rotation2d.fromDegrees(0.0)),
            Pose2d(18.651.feet, 7.997.feet, Rotation2d.fromDegrees(-23.0)),
            Pose2d(22.13.feet, 2.612.feet, Rotation2d.fromDegrees(-148.0))
        ),
        listOf(CentripetalAccelerationConstraint(7.feet.acceleration)),
        0.0.meters.velocity,
        0.0.meters.velocity,
        8.feet.velocity,
        3.0.feet.acceleration,
        false
    )

    val trajectory2 = DefaultTrajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(22.13.feet, 2.612.feet, Rotation2d.fromDegrees(-148.0)),
            Pose2d(18.651.feet, 7.997.feet, Rotation2d.fromDegrees(-23.0)),
            Pose2d(5.326.feet, 9.697.feet, Rotation2d.fromDegrees(0.0))

        ),
        listOf(CentripetalAccelerationConstraint(7.feet.acceleration)),
        0.0.meters.velocity,
        0.0.meters.velocity,
        8.feet.velocity,
        3.0.feet.acceleration,
        true
    )
>>>>>>> Test trajectory tracking

    // Constructor of the Robot class.
    init {
        +Drivetrain
    }

    // Runs once when robot boots up
    override fun robotInit() {
        // Add auto mode selector to Shuffleboard
        Shuffleboard.getTab("5190").add(autoModeSelector)
    }

    // Runs once when autonomous period starts
    override fun autonomousInit() {
        // Start the autonomous routine with the selected auto mode from the
        // sendable chooser.
        Autonomous.start(autoModeSelector.selected)

        Drivetrain.resetPosition(Pose2d(5.326.feet, 9.697.feet, Rotation2d.fromDegrees(0.0)))
        sequential {
            +Drivetrain.followTrajectory(trajectory)
            +Drivetrain.followTrajectory(trajectory2)
        }.schedule()
    }

    // Runs once when teleop period starts
    override fun teleopInit() {}

    // Runs once when robot is disabled
    override fun disabledInit() {}

    // Runs every 20 ms when robot is on
    override fun robotPeriodic() {
        Shuffleboard.update()
    }

    // Runs every 20 ms when autonomous is enabled
    override fun autonomousPeriodic() {}

    // Runs every 20 ms when teleop is enabled
    override fun teleopPeriodic() {}

    // Runs every 20 ms when robot is disabled
    override fun disabledPeriodic() {}
}
