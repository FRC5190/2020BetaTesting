package org.ghrobotics.frc2019.auto.routines

import edu.wpi.first.wpilibj.geometry.Rotation2d
import org.ghrobotics.frc2019.commands.BalanceTeeterTotterCommand
import org.ghrobotics.frc2019.commands.ConstantVelocityDriveCommand
import org.ghrobotics.frc2019.subsystems.Drivetrain
import org.ghrobotics.lib.commands.sequential
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.operations.div
import org.ghrobotics.lib.mathematics.units.seconds

class BalanceTeeterTotterRoutine : AutoRoutine {
    override val name = "Balance Teeter Totter"
    override val routine = sequential {
        +ConstantVelocityDriveCommand(2.0.feet / 1.seconds)
            .withInterrupt { (Drivetrain.getPitch() - Rotation2d()).degrees > 10.0 }
        +BalanceTeeterTotterCommand()
    }
}