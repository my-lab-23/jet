package com.example

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation
import org.apache.commons.math3.stat.regression.SimpleRegression
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.selectAll

object Signals : IntIdTable() {
    val ssid = varchar("ssid", 50)
    val signalStrength = integer("signalStrength")
    val timeStamp = long("timeStamp")
}

object Coords : IntIdTable() {
    val x = integer("x")
    val y = integer("y")
    val timeStamp = long("timeStamp")
}

object MyDB {

    fun connectDB() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/expo", driver = "org.postgresql.Driver",
            user = "expo", password = "expo"
        )
    }

    fun resetTableSignals() {
        SchemaUtils.drop(Signals)
        SchemaUtils.create(Signals)
    }

    fun resetTableCoords() {
        SchemaUtils.drop(Coords)
        SchemaUtils.create(Coords)
    }

    fun insertSignal(signal: Signal) {
        Signals.insert {
            it[ssid] = signal.ssid
            it[signalStrength] = signal.signalStrength
            it[timeStamp] = signal.timeStamp
        }
    }

    fun insertCoord(coord: Coord) {
        Coords.insert {
            it[x] = coord.x
            it[y] = coord.y
            it[timeStamp] = coord.timeStamp
        }
    }

    fun getLastNSignals(n: Int): List<Signal> = transaction {
        Signals.selectAll().orderBy(Signals.id to SortOrder.DESC).limit(n).map {
            Signal(it[Signals.ssid], it[Signals.signalStrength], it[Signals.timeStamp])
        }
    }

    fun getLastNCoords(n: Int): List<Coord> = transaction {
        Coords.selectAll().orderBy(Coords.id to SortOrder.DESC).limit(n).map {
            Coord(it[Coords.x], it[Coords.y], it[Coords.timeStamp])
        }
    }

    //

    fun expectedTime(): Double {

        val regression = SimpleRegression()

        val signalStrengthData = mutableListOf<Double>()
        val timeStampData = mutableListOf<Double>()

        Signals.selectAll().forEach {
            val signalStrength = it[Signals.signalStrength]
            val timeStamp = it[Signals.timeStamp].toDouble()

            signalStrengthData.add(signalStrength.toDouble())
            timeStampData.add(timeStamp)
        }

        val outlierThreshold = 2.0

        val signalStrengthAverage = signalStrengthData.average()
        val signalStrengthStdDev = calculateStandardDeviation(signalStrengthData)
        val timeStampAverage = timeStampData.average()
        val timeStampStdDev = calculateStandardDeviation(timeStampData)

        val filteredData = signalStrengthData.zip(timeStampData)
            .filter { (signalStrength, timeStamp) ->
                signalStrength in (signalStrengthAverage - outlierThreshold * signalStrengthStdDev)..
                        (signalStrengthAverage + outlierThreshold * signalStrengthStdDev) &&
                        timeStamp in (timeStampAverage - outlierThreshold * timeStampStdDev)..
                        (timeStampAverage + outlierThreshold * timeStampStdDev)
            }

        filteredData.forEach { (signalStrength, timeStamp) ->
            regression.addData(signalStrength, timeStamp)
        }

        val slope = regression.slope
        val intercept = regression.intercept

        val signalStrengthValue = 100

        return slope * signalStrengthValue + intercept
    }

    private fun calculateStandardDeviation(data: List<Double>): Double {

        val stdDev = StandardDeviation()
        return stdDev.evaluate(data.toDoubleArray())
    }
}
