package com.example.novena

import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import jnovena.SerialGenerator
import java.io.File
import java.io.FileWriter

object Aux {

    private val serial: String = SerialGenerator.generateSerial()

    fun getSerial(): String { return serial }

    fun highlightSquare(gridPane: GridPane, currentDay: Int) {

        val children = gridPane.children

        val targetIndex = when (currentDay) {
            in 1..9 -> currentDay - 1
            in 10..18 -> (currentDay - 10)
            in 19..27 -> (currentDay - 19)
            in 27..31 -> (currentDay - 27)
            else -> -1
        }

        if (targetIndex >= 0 && targetIndex < children.size) {
            val rectangle = children[targetIndex] as Rectangle
            rectangle.fill = Color.RED
        }
    }

    fun applyIdFromFile(gridPane: GridPane, filePath: String) {
        val file = File(filePath)

        try {
            val lines = file.readLines()

            for (i in 0..8) {
                val id = lines[i].trim()
                val node = gridPane.children[i]

                // Assumendo che l'ID debba essere applicato a un certo tipo di nodo nel GridPane,
                // puoi impostare l'ID in base alle tue esigenze.
                node.id = id
            }

            printRectangleIds(gridPane, "/home/ema/saved.txt")

        } catch (e: Exception) { println("") }
    }

    fun resetIdsIfAllGreen(gridPane: GridPane) {
        var allGreen = true

        for (node in gridPane.children) {
            if (node is Rectangle) {
                if (node.id != "green") {
                    allGreen = false
                    break
                }
            }
        }

        if (allGreen) {
            for (node in gridPane.children) {
                if (node is Rectangle) {
                    node.id = null
                }
            }
        }
    }

    fun colorSquaresWithId(gridPane: GridPane) {
        for (node in gridPane.children) {
            if (node is Rectangle && node.id == "green") {
                node.fill = Color.GREEN
            }
        }
    }

    fun printRectangleIds(gridPane: GridPane, filePath: String) {
        val file = File(filePath)

        try {
            val writer = FileWriter(file)

            for (node in gridPane.children) {
                if (node is Rectangle) {
                    println(node.id)
                    writer.write(node.id + "\n")
                }
            }

            writer.close()
            println("Gli ID dei rettangoli sono stati scritti correttamente nel file: $filePath")
        } catch (e: Exception) {
            println("Si è verificato un errore durante la scrittura dei rettangoli nel file.")
            e.printStackTrace()
        }
    }
}