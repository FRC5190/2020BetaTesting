/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.vision

import com.fazecast.jSerialComm.SerialPort
import edu.wpi.first.wpilibj.Timer
import org.ghrobotics.lib.mathematics.units.seconds
import kotlin.concurrent.fixedRateTimer

/**
 * Manages various JeVois cameras.
 */
object JeVoisManager {
    private val connectedCameras = mutableListOf<JeVois>()

    init {
        fixedRateTimer(name = "JeVoisManager", period = 10000L) {
            val currentTime = Timer.getFPGATimestamp().seconds
            connectedCameras.removeIf {
                it.update(currentTime)
                if (!it.isAlive) {
                    println("[JeVois Manager]: Disconnected Camera: ${it.systemPortName}")
                    it.dispose()
                    true
                } else {
                    false
                }
            }

            println(SerialPort.getCommPorts().map { it.descriptivePortName })

            val jevoisPorts = SerialPort.getCommPorts().filter { it.descriptivePortName.contains("JeVois", true) }
            for (port in jevoisPorts) {
                if (connectedCameras.any { it.systemPortName.equals(port.systemPortName, true) }) {
                    continue
                }
                println("[JeVois Manager] Found new camera: ${port.systemPortName}")
                connectedCameras.add(JeVois(port))
            }
        }
    }
}
