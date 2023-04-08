package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import java.io.File
import io.ktor.server.sessions.get

fun Application.prenotazioni() {

    routing {

        get("/other") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession != null) {

                call.respondFile(File("$ws/prenotazioni.html"))

            } else { call.respondRedirect("/other/login") }
        }

        post("/other/prenotazioni") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession != null) {

                val jsonString = call.receive<String>()
                call.respond(HttpStatusCode.OK)
                println("--- --- -")
                println("--- --- - json: $jsonString")
                println("--- --- -")

            } else { call.respondRedirect("/other/login") }
        }

        get("/other/grazie") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession != null) {

                call.respondFile(File("$ws/grazie.html"))

            } else { call.respondRedirect("/other/login") }
        }

        //

        post("/other/prenotazioni/android") {

            val accessToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")

            if(accessToken==null) {

                call.respondText("Accesso negato (manca il token).")

            } else {

                val userInfo = UserInfo.getUserInfoFromAuth0(accessToken)

                if(userInfo.email==System.getenv("USER_EMAIL")) {

                    val jsonString = call.receive<String>()
                    call.respond(HttpStatusCode.OK)
                    println("--- --- -")
                    println("--- --- - json: $jsonString")
                    println("--- --- -")

                } else { call.respondText("Accesso negato.") }
            }
        }
    }
}
