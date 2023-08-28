package com.example.trentadue

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trentadue.ui.theme.TrentadueTheme
import kotlinx.coroutines.delay
import java.util.regex.Pattern

const val apiKey = ""

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrentadueTheme {

                Column(modifier = Modifier.padding(20.dp, 20.dp)) {
                    CartesianPlaneWithPoints()
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun CartesianPlaneWithPoints() {

    var points by remember { mutableStateOf(emptySet<Offset>()) }
    var coords by remember { mutableStateOf(emptySet<String>()) }

    LaunchedEffect(true) {

        while (true) {

            val s = MyClient.read()
            val pattern = "Coord\\(x=(-?\\d+), y=(-?\\d+),"
            val regex = Pattern.compile(pattern)
            val matcher = regex.matcher(s)

            if (matcher.find()) {
                val x = matcher.group(1)?.toFloat() ?: 0f
                val y = matcher.group(2)?.toFloat() ?: 0f

                val x1 = (x * 4) + 400
                val y1 = -(y * 4) + 400

                Log.d("Coords", x1.toString())
                Log.d("Coords", y1.toString())

                points = points + Offset(x1+20, y1+20)
                coords = coords + s

                delay(5000)
            }
        }
    }

    val color = Color.Blue
    val textMeasurer = rememberTextMeasurer()

    Row {

        Canvas(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Black))
                .size(420.dp, 420.dp)
                .background(Color.LightGray)
        ) {
            points.forEach {

                drawCircle(
                    color = color,
                    radius = 10f,
                    center = it
                )
            }

            drawLine(
                Color.Black,
                Offset(size.width / 2, 0f), Offset(size.width / 2, size.height)
            )
            drawLine(
                Color.Black,
                Offset(0f, size.height / 2), Offset(size.width, size.height / 2)
            )

            drawText(textMeasurer, "y=100", Offset((size.width / 2) + 10, 10f))
            drawText(textMeasurer, "x=100", Offset(size.width - 100, (size.height / 2) + 10))
        }

        Box(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 20.dp)) {
            Text(coords.joinToString("\n"), fontSize=20.sp)
        }
    }
}
