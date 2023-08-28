package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    io.ktor.server.jetty.EngineMain.main(args)
}

fun Application.module() {

    auth()
    cors()
    routing()

    MyDB.connectDB()
}
