package org.ghrobotics.frc2019.commands

import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.twodim.control.TrajectoryTrackerOutput
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.LinearVelocity

class ConstantVelocityDriveCommand(private val speed: SIUnit<LinearVelocity>) : FalconCommand(Drivetrain) {

    override fun execute() {
        println("executing")
        Drivetrain.setOutput(
            TrajectoryTrackerOutput(
                speed, SIUnit(0.0), SIUnit(0.0), SIUnit(0.0)
            )
        )
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.setNeutral()
    }
}