package org.ghrobotics.frc2019

import com.revrobotics.CANSparkMaxLowLevel
import org.ghrobotics.lib.mathematics.units.inch
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits

object Constants {

    //DRIVETRAIN
    const val kLeftMasterId = 1
    const val kLeftSlave1Id = 2
    const val kRightMasterId = 3
    const val kRightSlave1Id = 4
    val kDriveWheelRadius = 3.inch
    val kDriveUnitsPerRotation = 306.18.nativeUnits
    val kDriveNativeUnitModel = NativeUnitLengthModel(kDriveUnitsPerRotation, kDriveWheelRadius)
}