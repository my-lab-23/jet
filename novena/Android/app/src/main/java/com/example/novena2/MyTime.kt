package com.example.novena2

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
object MyTime {

    private val currentDate = LocalDate.now()
    private val currentDay = currentDate.dayOfMonth

    fun getCurrentDay(): Int {
        return currentDay
    }
}
