package com.example

import com.example.my.configureGemini
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    configureFuel()

    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
    configureGemini()
}
