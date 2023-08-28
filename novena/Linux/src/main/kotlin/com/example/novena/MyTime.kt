package com.example.novena

import java.time.LocalDate

object MyTime {

    private val currentDate = LocalDate.now()
    private val currentDay = currentDate.dayOfMonth

    fun getCurrentDay(): Int {
        return currentDay
    }
}
