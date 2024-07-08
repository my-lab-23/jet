package com.example.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Instant

fun Application.configureRouting() {

    install(ContentNegotiation) {
        
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(CORS) {
        anyHost()
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

        // Static plugin. Try to access `/static/index.html`
        staticFiles("/examples", File("./ws"))

        get("/") {
            call.respondRedirect("/examples/esperanza")
        }

        get("/examples/esperanza") {
            call.respondFile(File("./ws/index.html"))
        }

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
    val command = System.getenv("EXPO")
    val log = Runtime.getRuntime().exec(command)
    val reader = BufferedReader(InputStreamReader(log.errorStream))
    val errorOutput = reader.readLines().joinToString("\n")
    println("Output di errore: $errorOutput")
}

fun createDirectoryIfNotExists(directoryPath: String) {

    val directory = File(directoryPath)
    if (!directory.exists()) { directory.mkdir() }
}
