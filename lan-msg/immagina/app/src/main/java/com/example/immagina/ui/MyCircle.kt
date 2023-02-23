package com.example.immagina.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun MyCircle() {

    val switch = remember { mutableStateOf(false) }

    Column {

        Row(Modifier.weight(9f)) {

            Draw(switch.value)
        }

        Row(
            Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Button(onClick = {
                switch.value = true
            }) {
                Text("DRAW", fontSize = 20.sp)
            }

            Button(onClick = {
                switch.value = false
            }) {
                Text("CLEAN", fontSize = 20.sp)
            }
        }
    }
}

@Composable
private fun Draw(value: Boolean) {

    if(value) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            repeat(42) {

                val x = Random.nextInt(0, size.width.toInt()).toFloat()
                val y = Random.nextInt(0, size.height.toInt()).toFloat()
                val radius = Random.nextInt(10, 100)

                val color = Color(
                    red = Random.nextInt(256),
                    green = Random.nextInt(256),
                    blue = Random.nextInt(256),
                    255
                )

                drawCircle(
                    color = color,
                    radius = radius.dp.toPx(),
                    center = Offset(x = x, y = y)
                )
            }
        }
    }
}
