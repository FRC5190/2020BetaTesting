/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.subsystems

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.frc2019.commands.TeleopDriveCommand
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.volts
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.motors.rev.FalconMAX
import org.ghrobotics.lib.physics.MotorCharacterization
import org.ghrobotics.lib.subsystems.drive.FalconWestCoastDrivetrain
import org.ghrobotics.lib.utils.Source

object Drivetrain : FalconWestCoastDrivetrain() {

    // Private member variables
    private val navX = AHRS(SPI.Port.kMXP)

    // Overriden variables

    // Motors
    override val leftMotor =
        FalconMAX(
            Constants.Drivetrain.kLeftMasterId,
            CANSparkMaxLowLevel.MotorType.kBrushless,
            Constants.Drivetrain.kNativeUnitModel
        )
    override val rightMotor =
        FalconMAX(
            Constants.Drivetrain.kRightMasterId,
            CANSparkMaxLowLevel.MotorType.kBrushless,
            Constants.Drivetrain.kNativeUnitModel
        )
    private val leftSlave1 =
        FalconMAX(
            Constants.Drivetrain.kLeftSlave1Id,
            CANSparkMaxLowLevel.MotorType.kBrushless,
            Constants.Drivetrain.kNativeUnitModel
        )
    private val rightSlave1 =
        FalconMAX(
            Constants.Drivetrain.kRightSlave1Id,
            CANSparkMaxLowLevel.MotorType.kBrushless,
            Constants.Drivetrain.kNativeUnitModel
        )

    // Motor characterization
    override val leftCharacterization = MotorCharacterization<Meter>(SIUnit(1.75), SIUnit(0.0398), SIUnit(0.204))
    override val rightCharacterization = MotorCharacterization<Meter>(SIUnit(1.76), SIUnit(0.0601), SIUnit(0.191))

    // Gyro
    override val gyro: Source<Rotation2d> = { Rotation2d.fromDegrees(-navX.yaw.toDouble()) }

    // Kinematics
    override val kinematics = DifferentialDriveKinematics(Constants.Drivetrain.kTrackWidth.value)

    // Odometry
    override val odometry = DifferentialDriveOdometry(kinematics)

    // Trajectory tracker
    override val controller = RamseteController(Constants.Drivetrain.kBeta, Constants.Drivetrain.kZeta)

    // Constructor
    init {
        leftMotor.outputInverted = false
        rightMotor.outputInverted = true

        leftSlave1.follow(leftMotor)
        rightSlave1.follow(rightMotor)

        listOf(leftMotor, rightMotor).forEach { motor ->
            motor.canSparkMax.restoreFactoryDefaults()
            motor.encoder.resetPosition(0.meters)
            motor.brakeMode = true
            motor.voltageCompSaturation = 12.volts
            motor.canSparkMax.setSmartCurrentLimit(38) // Amperes
        }

        defaultCommand = TeleopDriveCommand()
        recoverFromEmergency()
    }

    fun resetPosition(pose: Pose2d) {
        navX.zeroYaw()
        odometry.resetPosition(pose)
    }

    // Emergency management
    override fun activateEmergency() {
        listOf(leftMotor, rightMotor).forEach { motor ->
            motor.controller.p = 0.0
        }
    }

    override fun recoverFromEmergency() {
        listOf(leftMotor, rightMotor).forEach { motor ->
            motor.controller.p = Constants.Drivetrain.kP
        }
    }
}
