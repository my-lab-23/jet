package com.example

import com.example.my.configureMy
import com.example.my.configureMy2
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.jetty.EngineMain.main(args)
}

fun Application.module() {
    //configureSockets()
    //configureSerialization()
    //configureDatabases()
    //configureMonitoring()
    //configureHTTP()
    //configureSecurity()
    configureMy()
    configureMy2()
    configureRouting()
}
