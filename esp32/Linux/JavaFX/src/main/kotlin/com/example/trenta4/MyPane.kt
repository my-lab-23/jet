package com.example.trenta4

import javafx.geometry.Insets
import javafx.geometry.VPos
import javafx.scene.control.ScrollPane
import javafx.scene.layout.GridPane

object MyPane {

    val gridPane = GridPane()

    fun layout() {

        val scrollPane = ScrollPane(MyWidget.label)

        val buttonPane = GridPane()
        buttonPane.add(MyWidget.button0, 0, 0)
        buttonPane.add(MyWidget.button1, 1, 0)

        gridPane.add(MyWidget.canvas, 0, 0)
        gridPane.add(scrollPane, 1, 0)
        gridPane.add(buttonPane, 1, 1)

        scrollPane.padding = Insets(10.0)
        gridPane.padding = Insets(20.0)
        GridPane.setValignment(MyWidget.label, VPos.TOP)
        GridPane.setMargin(MyWidget.canvas, Insets(0.0, 20.0, 0.0, 0.0))
    }
}
