/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.subsystems

import com.ctre.phoenix.sensors.PigeonIMU
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.frc2019.commands.TeleopDriveCommand
import org.ghrobotics.lib.mathematics.twodim.control.RamseteTracker
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.inch
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits
import org.ghrobotics.lib.motors.FalconMotor
import org.ghrobotics.lib.motors.rev.FalconMAX
import org.ghrobotics.lib.physics.MotorCharacterization
import org.ghrobotics.lib.subsystems.drive.FalconWestCoastDrivetrain
import org.ghrobotics.lib.utils.Source
import org.ghrobotics.lib.utils.asSource

object Drivetrain : FalconWestCoastDrivetrain() {

    // Constants are at the top of each subsystem.
    // These must be private.

    private const val kPigeonId = 17

    private const val kBeta = 2.0
    private const val kZeta = 0.7

    // Private member variables
    private val pigeon = PigeonIMU(kPigeonId)

    // Overriden variables

    // Motors
    override val leftMotor = FalconMAX(Constants.kLeftMasterId, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.kDriveNativeUnitModel)
    override val rightMotor = FalconMAX(Constants.kRightMasterId, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.kDriveNativeUnitModel)
    private val leftSlave1 = FalconMAX(Constants.kLeftSlave1Id, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.kDriveNativeUnitModel)
    private val rightSlave1 = FalconMAX(Constants.kRightSlave1Id, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.kDriveNativeUnitModel)

    // Motor characterization
    override val leftCharacterization: MotorCharacterization<Meter> = TODO()
    override val rightCharacterization: MotorCharacterization<Meter> = TODO()

    // Gyro
    override val gyro: Source<Rotation2d> = pigeon.asSource()

    // Kinematics
    override val kinematics: DifferentialDriveKinematics = TODO()

    // Odometry
    override val odometry = DifferentialDriveOdometry(kinematics)

    // Trajectory tracker
    override val trajectoryTracker = RamseteTracker(kBeta, kZeta)

    // Constructor
    init {
        defaultCommand = TeleopDriveCommand()
    }

    // Emergency management.
    override fun activateEmergency() {
        TODO()
    }

    override fun recoverFromEmergency() {
        TODO()
    }
}