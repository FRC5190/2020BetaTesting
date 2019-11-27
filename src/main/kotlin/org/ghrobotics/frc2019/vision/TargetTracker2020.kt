/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.vision

import edu.wpi.first.wpilibj.geometry.Pose2d
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.Second
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.seconds
import org.ghrobotics.lib.vision.TargetTracker

/**
 * Represents the instance of target tracker that is specific to the 2020
 * beta testing. (Uses 2019 game)
 */
object TargetTracker2020 : TargetTracker(TargetTrackerConstants(1.5.seconds, 15.inches)) {
    /**
     * Adds one new sample to the target tracker.
     */
    fun addSample(captureTime: SIUnit<Second>, pose: Pose2d) {
        super.addSamples(captureTime, listOf(pose))
    }

    /**
     * Returns the closest target that is in front of the robot.
     *
     * @param robotPose The position of the robot.
     */
    fun getBestTarget(robotPose: Pose2d): TrackedTarget? {
        return targets
            .filter { it.isAlive && it.averagePose.translation.x > 0 }
            .minBy { robotPose.translation.getDistance(it.averagePose.translation) }
    }
}
