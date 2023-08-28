package com.example.trenta4

import javafx.application.Platform
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.paint.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

object MyWidget {

    val canvas = Canvas(420.0, 420.0)
    private val gc: GraphicsContext = canvas.graphicsContext2D

    val label = Label("Raw input from server:")
    val button0 = Button("COPY")
    val button1 = Button("STOP")

    //

    private val points = mutableSetOf<Pair<Float, Float>>()
    private val coords = mutableSetOf<String>()

    fun button0() {

        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        button0.setOnMouseClicked {
            content.putString(label.text)
            clipboard.setContent(content)
        }
    }

    fun cycle() {

        val scope = CoroutineScope(Dispatchers.IO)

        var stop = false
        button1.setOnMouseClicked { stop = true }

        scope.launch {
            while (!stop) {

                val s = MyClient.read()

                val pattern = "Coord\\(x=(-?\\d+), y=(-?\\d+),"
                val regex = Pattern.compile(pattern)
                val matcher = regex.matcher(s)

                if (matcher.find()) {
                    val x = matcher.group(1)?.toFloat() ?: 0f
                    val y = matcher.group(2)?.toFloat() ?: 0f

                    val x1 = (x * 2) + 200
                    val y1 = -(y * 2) + 200

                    points.add(x1 to y1)
                    coords.add(s)

                    Platform.runLater {
                        drawCartesianPlane()
                        drawPoints()
                        drawCoords()
                    }

                    delay(5000)
                }
            }
        }.start()
    }

    private fun drawCartesianPlane() {
        gc.clearRect(0.0, 0.0, 420.0, 420.0)
        gc.stroke = Color.BLACK

        // Draw x and y axes
        gc.strokeLine(210.0, 0.0, 210.0, 420.0)
        gc.strokeLine(0.0, 210.0, 420.0, 210.0)

        // Draw labels
        gc.fillText("y=100", 220.0, 10.0)
        gc.fillText("x=100", 370.0, 230.0)
    }

    private fun drawPoints() {
        gc.fill = Color.BLUE
        points.forEach { (x, y) ->
            gc.fillOval(x.toDouble(), y.toDouble(), 10.0, 10.0)
        }
    }

    private fun drawCoords() {
        val l = "Raw input from server:\n"
        label.text = l + coords.joinToString("\n")
    }
}
