/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import org.ghrobotics.frc2019.auto.Autonomous
import org.ghrobotics.frc2019.auto.Paths
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.frc2019.vision.TargetTracker2020
import org.ghrobotics.frc2019.vision.VisionProcessing
import org.ghrobotics.lib.wrappers.FalconTimedRobot
import org.ghrobotics.lib.wrappers.networktables.enumSendableChooser

object Robot : FalconTimedRobot() {

    // Chooser for the auto mode.
    private val autoModeSelector = enumSendableChooser<Autonomous.Mode>()
    private val pitch = Shuffleboard.getTab("5190").add("Pitch", 0.0).entry

    // Constructor of the Robot class.
    init {
        Paths

        +Drivetrain
        +VisionProcessing
    }

    // Runs once when robot boots up
    override fun robotInit() {
        // Add auto mode selector to Shuffleboard
        val tab = Shuffleboard.getTab("5190")
        tab.add("Auto Selector", autoModeSelector)
    }

    // Runs once when autonomous period starts
    override fun autonomousInit() {
        // Start the autonomous routine with the selected auto mode from the
        // sendable chooser.
        Autonomous.start(autoModeSelector.selected)
    }

    // Runs once when teleop period starts
    override fun teleopInit() {}

    // Runs once when robot is disabled
    override fun disabledInit() {}

    // Runs every 20 ms when robot is on
    override fun robotPeriodic() {
        Shuffleboard.update()
        TargetTracker2020.update()
    }

    // Runs every 20 ms when autonomous is enabled
    override fun autonomousPeriodic() {}

    // Runs every 20 ms when teleop is enabled
    override fun teleopPeriodic() {
        Controls.update()
    }

    // Runs every 20 ms when robot is disabled
    override fun disabledPeriodic() {}
}
