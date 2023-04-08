package com.example.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.logging.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.html.*
import kotlinx.serialization.json.Json
import java.io.File
import org.slf4j.event.Level
import java.time.Instant

fun Application.configureRouting() {

    install(ContentNegotiation) {
        
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(CORS) {
        
        allowHost("ema42esperanza.netlify.app", schemes = listOf("https"))
        allowHeader(HttpHeaders.ContentType)
    }

    install(CallLogging) {
        
        level = Level.INFO
        filter { call ->

            call.request.headers.contains(HttpHeaders.Origin)
        }
        format {

            val requestLine = it.request.toLogString()
            val corsHeader = it.request.headers[HttpHeaders.Origin]
            "CORS call: $requestLine, Origin: $corsHeader"
        }
    }

    routing {

        get("/examples") {

            call.respondHtml {
                head {
                    title { +"Semplice Landing Page" }
                }
                body {
                    h1 { +"Benvenuti sulla mia Landing Page" }
                    p { +"Questa è una prova di una semplice landing page creata con Ktor." }
                }
            }
        }

        post("/examples/msg") {

            val jsonString = call.receive<String>()
            call.respond(HttpStatusCode.OK)

            println("json: $jsonString")
            File("/home/ktor/msg.txt").appendText("$jsonString\n")

            val scope = CoroutineScope(Dispatchers.IO)

            scope.launch { toDB(jsonString) }
        }
    }
}

fun toDB(jsonString: String) {

    createDirectoryIfNotExists("/home/ktor/data")
    val timeStamp = Instant.now().toEpochMilli()
    File("/home/ktor/data/$timeStamp").writeText(jsonString)
    val command = "/home/ktor/Expo-1.0-SNAPSHOT/bin/Expo"
    Runtime.getRuntime().exec(command)
}

fun createDirectoryIfNotExists(directoryPath: String) {

    val directory = File(directoryPath)
    if (!directory.exists()) { directory.mkdir() }
}
