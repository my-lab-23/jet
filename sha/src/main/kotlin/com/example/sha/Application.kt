package com.example.sha

import com.example.sha.Sha.calculateSHA512Sum
import com.example.sha.Sha.findShaByFilename
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import java.io.File
import java.util.*

class ShaSumCheckerApp : Application() {

    private val selectedDirectoryLabel: Label = Label("Cartella selezionata: /home/ema/Scaricati")
    private val selectDirectoryButton = Button("Seleziona Cartella")
    private val processFilesButton = Button("Controlla ShaSum")

    override fun start(primaryStage: Stage) {

        selectDirectoryButton.setOnAction { selectDirectory() }
        processFilesButton.setOnAction { startTask() }

        val vbox = VBox(10.0)
        vbox.padding = Insets(10.0)
        vbox.children.addAll(selectDirectoryButton, selectedDirectoryLabel, processFilesButton)

        val scene = Scene(vbox, 600.0, 100.0)

        primaryStage.title = "Controlla ShaSum"
        primaryStage.scene = scene
        primaryStage.show()
    }

    private fun selectDirectory() {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "Seleziona una cartella"

        val selectedDirectory = directoryChooser.showDialog(null)
        if (selectedDirectory != null) {
            selectedDirectoryLabel.text = "Cartella selezionata: ${selectedDirectory.absolutePath}"
        } else {
            selectedDirectoryLabel.text = "Nessuna cartella selezionata."
        }
    }

    private fun startTask() {
        val task = Runnable {
            setButtons(true)
            val res = processFiles()
            setButtons(false)
            Platform.runLater {
                if(res) {
                    selectedDirectoryLabel.text = "ShaSum corretta!"
                } else {
                    selectedDirectoryLabel.text = "Errore!"
                }
            }
        }
        val backgroundThread = Thread(task)
        backgroundThread.isDaemon = true
        backgroundThread.start()
    }

    private fun setButtons(isDisable: Boolean) {
        selectDirectoryButton.isDisable = isDisable
        processFilesButton.isDisable = isDisable
    }

    private fun processFiles(): Boolean {
        Platform.runLater {
            selectedDirectoryLabel.text = "Sto calcolando..."
        }

        val selectedDirectory = selectedDirectoryLabel.text.replace("Cartella selezionata: ", "")
        val directory = File(selectedDirectory)

        val isoFile = directory.listFiles { file -> file.isFile && file.name.endsWith(".iso") }?.firstOrNull()
        val shaFile = directory.listFiles { file -> file.isFile && file.name.endsWith(".txt") }?.firstOrNull()

        return if (isoFile != null && shaFile != null) {
            validateFiles(isoFile, shaFile)
        } else {
            false
        }
    }

    private fun validateFiles(isoFile: File, shaFile: File): Boolean {

        val sha = findShaByFilename(shaFile.path, isoFile.name)
        var result = false
        val shaIso = calculateSHA512Sum(isoFile.path)

        if(sha==shaIso.lowercase(Locale.getDefault())) {
            result = true
        }

        return result
    }
}

fun main() {
    Application.launch(ShaSumCheckerApp::class.java)
}
