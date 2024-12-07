package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.concurrent.ConcurrentHashMap

// Mappa globale per associare email ai codici generati
val emailToCodeMap = ConcurrentHashMap<String, String>()

fun Application.configureRouting() {
    routing {

        get("/ktest/list") {
            val oauthCookie = call.getOauthCookie()
            val userProfile = getUserProfileFromOAuth(oauthCookie)
            val email = userProfile?.email ?: "Non disponibile"

            if(email==System.getenv("ADMIN_EMAIL")) {
                call.respondText(emailToCodeMap.toString())
            } else {
                call.respondText("Forbidden.")
            }
        }

        //

        staticResources("/ktest/vue", "static")
        authStatusRoute()

        //

        get("/ktest/skip") {
            call.respondText("Skipped - Ansible code: 1.4")
        }

        //

        get("/ktest/profile") {
            try {
                val oauthCookie = call.getOauthCookie()
                val userProfile = getUserProfileFromOAuth(oauthCookie)

                val email = userProfile?.email ?: "Non disponibile"
                call.respondText("Email dell'utente: $email", ContentType.Text.Plain)
            } catch (e: Exception) {
                println("Errore nel recuperare il profilo utente: ${e.message}")
                call.respondText("Errore nel recuperare il profilo utente", ContentType.Text.Plain, HttpStatusCode.InternalServerError)
            }
        }

        get("/ktest") {
            val profileLink = """<a href="/ktest/profile">Vai al profilo utente</a>"""
            val htmlResponse = """
            <html>
            <head>
                <title>Benvenuto</title>
            </head>
            <body>
                <h1>Benvenuto nel server Ktor!</h1>
                <p>$profileLink</p>
            </body>
            </html>
            """
            call.respondText(htmlResponse, ContentType.Text.Html)
        }

        //

        get("/ktest/api/system-info") {
            try {
                val systemInfo = getSystemInfo()
                call.respond(systemInfo)
            } catch (e: Exception) {
                call.respond(mapOf("error" to "Impossibile recuperare le informazioni di sistema"))
            }
        }

        post("/ktest/android/api/system-info") {
            val receivedKey = call.receiveParameters()["androidKey"]

            if (receivedKey.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Chiave Android mancante."))
                return@post
            }

            val email = emailToCodeMap.entries.find { it.value == receivedKey }?.key

            if (email != null) {
                val systemInfo = getSystemInfo()
                call.respond(systemInfo)
            } else {
                call.respond(HttpStatusCode.Unauthorized, mapOf("message" to "Chiave Android non valida."))
            }
        }
    }
}
