package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import java.io.File

fun Application.postlog() {

    routing {

        get("/other/post-login") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession != null) {

                call.respondFile(File("$ws/post-login.html"))

            } else {
                call.respondText("Non sei ancora loggato.")
            }
        }

        get("/other/post-logout") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession == null) {

                call.respondFile(File("$ws/post-logout.html"))

            } else {
                call.respondText("Non sei più loggato.")
            }
        }
    }
}
