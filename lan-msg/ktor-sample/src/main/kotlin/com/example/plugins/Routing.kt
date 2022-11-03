package com.example.plugins

import com.lordcodes.turtle.shellRun
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileWriter

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("OK!")
        }
        get("/msg/{msg}") {
            val msg = call.parameters["msg"].toString() + "|*|"
            withContext(Dispatchers.IO) {
                FileWriter("/home/ema/data/data", true).use { it.write(msg) }
            }
        }
        get("/del") {
            withContext(Dispatchers.IO) {
                FileWriter("/home/ema/data/data", false).use { it.write("") }
            }
        }
        get("/pwd") {
            val output = shellRun("pwd")
            call.respondText(output)
        }
    }
    routing {
    }
}
