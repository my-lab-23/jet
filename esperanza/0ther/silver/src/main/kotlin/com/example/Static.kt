package com.example

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.static() {

    routing {

        staticFiles("/test", File("/home/ema/test/app/ws"))
    }
}
