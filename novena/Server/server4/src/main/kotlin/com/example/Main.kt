package com.example

import dev.forst.ktor.apikey.apiKey
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import org.slf4j.event.Level
import java.io.File
import java.time.Duration

val clients = mutableListOf<Pair<String, WebSocketSession>>()

fun main() {

    val path = "/home/ktor/data/"

    embeddedServer(Netty, port = 5000) {

        install(CallLogging) {
            level = Level.ERROR
        }

        intercept(Plugins) {
            val requestHeaders = call.request.headers
            val headersMap = requestHeaders.toMap()
            println("Headers received: $headersMap")
            proceed()
        }

        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }

        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Options)
            allowHeader("X-api-key")
            anyHost()
        }

        //

        val expectedApiKey = System.getenv("NOVENA_API_KEY")

        data class AppPrincipal(val key: String) : Principal

        install(Authentication) {
            apiKey {
                validate { keyFromHeader ->
                    keyFromHeader
                        .takeIf { it == expectedApiKey }
                        ?.let { AppPrincipal(it) }
                }
            }
        }

        //

        routing {

            authenticate {

                route("/novena/write/{client}") {
                    post {
                        val client = call.parameters["client"]
                        val content = call.receiveText()
                        val filePath = "${path}saved.txt"

                        try {
                            File(filePath).writeText(content)
                            broadcastMessage("$client ha scritto!")
                            call.respondText("Contenuto scritto con successo nel file.")
                        } catch (e: Exception) {
                            call.respondText("Si è verificato un errore durante la scrittura del file.")
                        }
                    }
                }

                route("/novena/read") {
                    get {
                        val filePath = "${path}saved.txt"

                        try {
                            val content = File(filePath).readText()
                            broadcastMessage("\n$content")
                            call.respondText(content)
                        } catch (e: Exception) {
                            call.respondText("Si è verificato un errore durante la lettura del file.")
                        }
                    }
                }

                webSocket("/novena/ws") {
                    handleWebSocketConversation(this)
                }
            }
        }
    }.start(wait = true)
}

suspend fun handleWebSocketConversation(session: WebSocketSession) {
    try {
        for (frame in session.incoming) {
            when (frame) {

                is Frame.Text -> {

                    val text = frame.readText()

                    if (text.contains("client")) {
                        if (clients.size < 9) {
                            clients.add(Pair(text, session))
                        }
                    } else if (text == "disconnect") {

                        val clientToRemove = clients.find { it.second == session }
                        clientToRemove?.let {
                            clients.remove(it)
                        }

                        session.close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnected"))
                    }
                }

                else -> {}
            }
        }
    } catch (e: Exception) {
        println("Error during WebSocket conversation: ${e.localizedMessage}")
    } finally {
        println("Client disconnected")
    }
}

suspend fun broadcastMessage(message: String) {
    for (client in clients) {
        try {
            client.second.send(Frame.Text(message))
        } catch (e: Throwable) {
            println("Errore durante l'invio del messaggio al client: ${e.localizedMessage}")
        }
    }
}
