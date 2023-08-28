package com.example.novena2

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

object Aux {

    private val serial: String = SerialGenerator.generateSerial()

    fun getSerial(): String { return serial }

    @RequiresApi(Build.VERSION_CODES.O)
    fun highlightSquare(): Int {

        val targetIndex = when (val currentDay = MyTime.getCurrentDay()) {
            in 1..9 -> currentDay - 1
            in 10..18 -> (currentDay - 10)
            in 19..27 -> (currentDay - 19)
            else -> -1
        }

        return targetIndex
    }

    fun convertToFalseArrayIfAllTrue(array: BooleanArray): BooleanArray {
        val allTrue = array.all { it }
        return if (allTrue) {
            BooleanArray(array.size) { false }
        } else {
            array.copyOf()
        }
    }
}
