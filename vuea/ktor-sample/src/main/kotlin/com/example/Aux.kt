package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import kotlin.random.Random

// Funzione ausiliaria per ottenere il cookie OAuth
fun ApplicationCall.getOauthCookie(): String? {
    return request.cookies["_oauth2_proxy"]
}

// Funzione per ottenere il profilo utente
suspend fun getUserProfileFromOAuth(oauthCookie: String?): UserProfile? {
    if (oauthCookie.isNullOrEmpty()) return null
    return try {
        UserProfileService.getUserProfile("https://0.0.0.0:443/oauth2/userinfo", oauthCookie)
    } catch (e: Exception) {
        null
    }
}

// Funzione per generare il codice
fun generateCode(length: Int, includeTimestamp: Boolean): String {
    require(length > 0) { "La lunghezza deve essere maggiore di 0." }

    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val randomCode = (1..length)
        .map { characters[Random.nextInt(characters.length)] }
        .joinToString("")

    return if (includeTimestamp) {
        val timestamp = LocalDateTime.now()
        "$timestamp-$randomCode"
    } else {
        randomCode
    }
}

//

fun Route.authStatusRoute() {
    route("/ktest/api/auth/status") {
        get {
            val oauthCookie = call.getOauthCookie()

            if (oauthCookie.isNullOrEmpty()) {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }

            val userProfile = getUserProfileFromOAuth(oauthCookie)
            if (userProfile != null) {
                val email = userProfile.email ?: "Non disponibile"
                val code = emailToCodeMap.computeIfAbsent(email) { generateCode(4, false) }

                call.respond(
                    HttpStatusCode.OK, mapOf(
                    "name" to (userProfile.name ?: "Utente"),
                    "email" to email,
                    "address" to (userProfile.address ?: "Non disponibile"),
                    "androidKey" to code
                )
                )
            } else {
                call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Errore nel recupero del profilo utente"))
            }
        }

        post {
            val receivedKey = call.receiveParameters()["androidKey"]

            if (receivedKey.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Chiave Android mancante."))
                return@post
            }

            val email = emailToCodeMap.entries.find { it.value == receivedKey }?.key

            if (email != null) {
                call.respond(
                    HttpStatusCode.OK, mapOf(
                    "message" to "Autenticazione riuscita",
                    "email" to email
                )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, mapOf("message" to "Chiave Android non valida."))
            }
        }
    }
}
