package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    io.ktor.server.jetty.EngineMain.main(args)
}

fun Application.module() {

    val currentPath = Paths.get("").toAbsolutePath().toString()
    println("Il percorso corrente è: $currentPath")
    File("./data").apply { if (!exists()) mkdirs() }
    
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
