import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import java.util.regex.Pattern

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        cartesianPlaneWithPoints()
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun cartesianPlaneWithPoints() {

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

                val x1 = (x * 2) + 200
                val y1 = -(y * 2) + 200

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
                    radius = 5f,
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
