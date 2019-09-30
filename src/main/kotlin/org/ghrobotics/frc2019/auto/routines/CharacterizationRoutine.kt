/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.auto.routines

import org.ghrobotics.frc2019.subsystems.Drivetrain

/**
 * Auto routine that characterizes the kV, kA, kS parameters of the
 * drivetrain. This routine should be used alongside the robotpy
 * robot-characterization toolsuite.
 */
object CharacterizationRoutine : AutoRoutine {
    override val name = "Characterization"
    override val routine
        get() = Drivetrain.characterize()
}
