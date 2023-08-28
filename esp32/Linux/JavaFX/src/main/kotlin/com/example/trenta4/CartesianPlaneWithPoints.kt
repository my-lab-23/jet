package com.example.trenta4

import com.example.trenta4.MyPane.layout
import com.example.trenta4.MyPane.gridPane
import com.example.trenta4.MyWidget.button0
import com.example.trenta4.MyWidget.cycle
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class CartesianPlaneWithPoints : Application() {

    override fun start(primaryStage: Stage) {

        val scene = Scene(gridPane, 840.0, 480.0)
        primaryStage.title = "Cartesian Plane with Points"
        primaryStage.scene = scene
        primaryStage.show()

        layout()
        button0()
        cycle()
    }
}

fun main() {
    Application.launch(CartesianPlaneWithPoints::class.java)
}
