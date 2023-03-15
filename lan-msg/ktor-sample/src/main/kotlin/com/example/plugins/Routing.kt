package com.example.plugins

import com.example.MyMessage
import com.lordcodes.turtle.shellRun
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileReader
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {

        get("/") {
            call.respondText("OK!")
        }

        post("/msg") {

            val p = call.receiveParameters()
            val msg = p["msg"].toString()

            withContext(Dispatchers.IO) {
                FileWriter("/home/ema/data/data", true).use {
                    it.write("$msg\n")
                }
            }

            val myMsg = MyMessage.fromJsonString(msg)
            val date = MyMessage.timestampToString(myMsg.timestamp)
            shellRun("notify-send", listOf(date, myMsg.msg))
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

        get("/shutdown") {
            shellRun("sudo", listOf("shutdown", "-h", "now"))
        }

        get("/img/{key}") {

            val key = call.parameters["key"].toString()
            shellRun("ruby", listOf("unsplash.rb", key))

            var img = ""

            withContext(Dispatchers.IO) {
                FileReader("img").buffered()
            }.use { img = it.readLine() }

            call.respondText(img)
        }
    }
}
