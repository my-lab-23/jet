package com.example.logo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MyApp() {

    val center = with(LocalDensity.current) { 200.dp.toPx() }

    val centerInput = MyInput(Offset(center, center), Offset(center, center), Color.Blue)

    if(MyCommands.check==0) {
        MyCommands.lastInput = centerInput
        MyCommands.centerInput = centerInput
        MyCommands.check = 1
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        val input = rememberSaveable { mutableStateOf(listOf(centerInput)) }

        Draw(input.value)

        //

        var command by rememberSaveable { mutableStateOf("") }

        TextField(
            value = command,
            onValueChange = { command = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
        )

        //

        Button(onClick = {

            val newInput = interpreter(command)

            if(newInput != null) {
                input.value = input.value + newInput
            } else {
                input.value = listOf(cs(MyCommands.lastInput))
            }

        }) {
            Text("DRAW", fontSize = 40.sp)
        }

        //

        for(s in Help.s) {

            Text(s, fontSize = 20.sp)
        }
    }
}

@Composable
fun Draw(inputList: List<MyInput>) {

    Canvas(modifier = Modifier
        .size(400.dp, 400.dp)
        .background(Color.LightGray)) {

        //

        val triangle = Path().apply {

            val x = inputList.last().end.x
            val y = inputList.last().end.y
            moveTo(x, y)
            lineTo(x+25, y+25)
            lineTo(x-25, y+25)
            close()
        }

        val x = inputList.last().end.x
        val y = inputList.last().end.y

        rotate(-MyCommands.angle, Offset(x, y)) {
            drawPath(path = triangle, color = inputList.last().c, style = Stroke(10f))
        }

        //

        for(input in inputList) {

            val path = Path().apply {

                moveTo(input.start.x, input.start.y)
                lineTo(input.end.x, input.end.y)
            }

            drawPath(path = path, color = input.c, style = Stroke(10f))
        }
    }
}
