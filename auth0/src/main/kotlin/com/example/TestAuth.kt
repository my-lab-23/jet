package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.testAuth() {

    routing {

        get("/") {
            call.respondRedirect("/other/testauth")
        }

        get("/other/testauth") {

            val userSession: UserSession? = call.sessions.get()
            var email = "sconosciuto"

            if (userSession != null) {

                val userInfo = UserInfo.getUserInfoFromAuth0(userSession.accessToken)
                email = userInfo.email
            }

            val html =
                """
                    <p>Salve $email!</p>
                    <p><a href="/other/login">Login</a></p>
                    <p><a href="/other/logout">Logout</a></p>
                """.trimIndent()

            call.respondText(html, ContentType.Text.Html, HttpStatusCode.OK)
        }
    }
}
