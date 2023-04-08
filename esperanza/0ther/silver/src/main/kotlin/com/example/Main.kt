package com.example

import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.cio.EngineMain.main(args)

//

fun Application.module() {

    auth()
    silver()
    static()
}
