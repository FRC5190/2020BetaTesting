/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2019, Green Hope Falcons
 */

package org.ghrobotics.frc2019.vision

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import edu.wpi.first.wpilibj.Timer
import org.ghrobotics.frc2019.Constants
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.Second
import org.ghrobotics.lib.mathematics.units.seconds

/**
 * Represents one JeVois camera.
 */
class JeVois(private val port: SerialPort) {

    // The system port name
    val systemPortName: String? = port.systemPortName

    // Debug
    private var lastMessageReceived = 0.seconds
    private var wasUnplugged = false

    var isAlive = true
        private set

    init {
        port.openPort()
        port.addDataListener(object : SerialPortDataListener {
            private var stringBuffer = StringBuilder(1024)
            override fun serialEvent(event: SerialPortEvent) {
                try {
                    if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                        return
                    }
                    val bytesAvailable = port.bytesAvailable()
                    if (bytesAvailable < 0) {
                        wasUnplugged = true
                        return
                    }
                    val newData = ByteArray(bytesAvailable)
                    port.readBytes(newData, newData.size.toLong())
                    for (newByte in newData) {
                        val newChar = newByte.toChar()
                        if (newChar != '\n') {
                            stringBuffer.append(newChar)
                            continue
                        }
                        processMessage(stringBuffer.toString())
                        stringBuffer.clear()
                    }
                } catch (e: Throwable) {
                    println("[JeVois] ${e.localizedMessage}")
                    e.printStackTrace()
                }
            }

            override fun getListeningEvents() = SerialPort.LISTENING_EVENT_DATA_AVAILABLE
        })
        lastMessageReceived = Timer.getFPGATimestamp().seconds
    }

    fun processMessage(message: String) {
        lastMessageReceived = Timer.getFPGATimestamp().seconds
        if (!message.startsWith('{')) {
            return
        }
        try {
            val jsonData = jevoisGson.fromJson<JsonObject>(message)

            val timestamp = Timer.getFPGATimestamp() - jsonData["capture_ago"].asDouble
            val contours = jsonData["targets"].asJsonArray
                .filterIsInstance<JsonObject>()

            VisionProcessor.processData(
                VisionData(
                    timestamp,
                    contours
                )
            )
        } catch (e: JsonParseException) {
            println("[JeVois] Got Invalid Data: $message")
        }
    }

    fun update(currentTime: SIUnit<Second>) {
        isAlive = !wasUnplugged && currentTime - lastMessageReceived <= Constants.Vision.kCameraTimeout
    }

    fun dispose() {
        port.closePort()
    }

    companion object {
        private val jevoisGson = Gson()
    }
}

data class VisionData(
    val timestamp: Double,
    val targets: List<JsonObject>
)
