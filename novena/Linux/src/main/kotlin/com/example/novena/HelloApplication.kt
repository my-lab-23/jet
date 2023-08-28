package com.example.novena

import com.example.novena.Aux.applyIdFromFile
import com.example.novena.Aux.colorSquaresWithId
import com.example.novena.Aux.highlightSquare
import com.example.novena.Aux.printRectangleIds
import com.example.novena.Aux.resetIdsIfAllGreen
import com.example.novena.Synchro.synchroBackup
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import jnovena.WebSocketClientExample
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI
import java.time.LocalDate
import kotlin.concurrent.thread

//

const val path = "/home/ema/data/"
const val adr = "wss://2desperados.it/novena/"

val apiKey: String = System.getenv("NOVENA_API_KEY")
val clientWebSocket = WebSocketClientExample(URI("${adr}ws"), apiKey)

val gridPane = GridPane()

//

class GridExample : Application() {

    override fun start(primaryStage: Stage) {

        //

        thread { clientWebSocket.connect() }

        //

        gridPane.alignment = Pos.CENTER
        gridPane.hgap = 10.0
        gridPane.vgap = 10.0

        val gridSize = 3
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                val rectangle = createRectangle(gridPane)
                gridPane.add(rectangle, col, row)
            }
        }

        startUp(0)

        val backgroundColor = Color.CYAN
        gridPane.style = "-fx-background-color: ${toRGBCode(backgroundColor)}"

        val scene = Scene(gridPane, 300.0, 300.0)
        primaryStage.title = "Novena"
        primaryStage.scene = scene
        primaryStage.show()

        //

        // Registra un gestore per l'evento di chiusura della finestra
        primaryStage.onHiding = EventHandler {
            // Esegui la funzione printRectangleIds() all'uscita del programma
            Platform.runLater {

                val scope1 = CoroutineScope(Dispatchers.IO)

                val c1 = scope1.launch {
                    printRectangleIds(gridPane, "${path}savedLinux.txt")
                    synchroBackup("${path}savedLinux.txt")
                }
                while(!c1.isCompleted) { /* --- */ }

                //

                thread { clientWebSocket.sendMsg("disconnect") }
            }
        }
    }

    private fun createRectangle(gridPane: GridPane): Rectangle {
        val rectangle = Rectangle(80.0, 80.0, Color.YELLOW)

        rectangle.setOnMouseClicked {
            rectangle.fill = Color.GREEN
            rectangle.id = "green"

            val scope2 = CoroutineScope(Dispatchers.IO)

            val c2 = scope2.launch {
                printRectangleIds(gridPane, "${path}savedLinux.txt")
                synchroBackup("${path}savedLinux.txt")
            }
            while(!c2.isCompleted) { /* --- */ }
        }
        return rectangle
    }

    private fun toRGBCode(color: Color): String {
        return String.format("#%02X%02X%02X",
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
    }
}

fun startUp(p: Int = 1) {

    val scope0 = CoroutineScope(Dispatchers.IO)

    val c0 = scope0.launch {
        val s = Synchro.read()
        if(s!="IOException") {
            File("${path}savedLinux.txt").writeText(s)
        }
    }
    while(!c0.isCompleted) { /* --- */ }

    val currentDay = MyTime.getCurrentDay()
    if(p==0) highlightSquare(gridPane, currentDay)
    applyIdFromFile(gridPane, "${path}savedLinux.txt")
    if(p==0) resetIdsIfAllGreen(gridPane)
    colorSquaresWithId(gridPane)
}

fun main(args: Array<String>) {

    Application.launch(GridExample::class.java, *args)
}
