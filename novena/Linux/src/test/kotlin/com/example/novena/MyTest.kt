package com.example.novena

import io.mockk.every
import io.mockk.mockkObject
import javafx.application.Application
import org.junit.jupiter.api.Test

class MyTest {

    private val customDay = 15

    @Test
    fun customDay() {

        mockkObject(MyTime)
        every { MyTime.getCurrentDay() } returns customDay

        Application.launch(GridExample::class.java)
    }
}
