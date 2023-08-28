package com.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class Signal(
    val ssid: String,
    val signalStrength: Int,
    val timeStamp: Long)

@Serializable
data class Coord(val x: Int, val y: Int, val timeStamp: Long)

object MySerialization {

    fun deserializeSignal(input: String) {

        val eInput = elabora(input)
        val signal: Signal = Json.decodeFromString(eInput)
        val e = signal.ssid
        val s = signal.signalStrength
        val tS = signal.timeStamp
        println("$e : $s : $tS")
        transaction { MyDB.insertSignal(Signal(e, s, tS)) }
    }

    fun deserializeCoord(input: String) {

        val eInput = elabora(input)
        val coord: Coord = Json.decodeFromString(eInput)
        val x = coord.x
        val y = coord.y
        val tS = coord.timeStamp
        println("x : $x - y : $y - tS : $tS")
        transaction { MyDB.insertCoord(Coord(x, y, tS)) }
    }

    private fun elabora(input: String): String {
        val eInput = input
            .removePrefix("\"")
            .removeSuffix("\"")
            .replace("\\", "")
        println(eInput)
        return eInput
    }
}
