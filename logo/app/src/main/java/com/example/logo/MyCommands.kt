package com.example.logo

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

object MyCommands {

    var angle = 0f
    var color = Color.Blue

    var check = 0
    var lastInput = MyInput(Offset(0f, 0f), Offset(0f, 0f), color)
    var centerInput = MyInput(Offset(0f, 0f), Offset(0f, 0f), color)
}

object Help {

    val s1 = "fd 100 - Avanza di 100 pixel."
    val s2 = "bk 150 - Torna indietro di 150 pixel."
    val s3 = "rt 45 - Ruota a destra di 45 gradi."
    val s4 = "lt 90 - Ruota a sinistra di 90 gradi."
    val s5 = "cs - Cancella il disegno."
    val s6 = "home - Torna al centro."

    val s = listOf(s1, s2, s3, s4, s5, s6)
}

fun forward(value: Float): MyInput {

    val sin = sin(Math.toRadians(MyCommands.angle.toDouble()))
    val cos = cos(Math.toRadians(MyCommands.angle.toDouble()))
    val l1 = value * sin.toFloat()
    val l2 = value * cos.toFloat()
    val newInput = MyInput(
        MyCommands.lastInput.end,
        Offset(MyCommands.lastInput.end.x + l1, MyCommands.lastInput.end.y + l2),
        MyCommands.color
    )

    MyCommands.lastInput = newInput

    return newInput
}

fun cs(lastInput: MyInput): MyInput {

    val x = lastInput.end.x
    val y = lastInput.end.y
    return MyInput(Offset(x, y), Offset(x, y), Color.Blue)
}
