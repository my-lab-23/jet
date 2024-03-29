package com.example

import com.example.Roots.DOMAIN
import com.example.UserInfo.getUserInfoFromAuth0
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import java.io.File

fun Application.silver() {

    routing {

        get("/$SILVER/android/{access_token}/{value}") {

            val accessToken = call.parameters["access_token"]
            val value = call.parameters["value"]

            if(accessToken==null) {

                call.respondText("Accesso negato (manca il token).")

            } else {

                val userInfo = getUserInfoFromAuth0(accessToken)

                if(userInfo.email==System.getenv("USER_EMAIL")) {

                    val result = getValues(value)
                    call.respondText(result)

                } else { call.respondText("Accesso negato.") }
            }
        }

        //

        get("/$SILVER/ws_html") {

            val userSession: Session? = call.sessions.get()

            if (userSession != null) {

                val userInfo = getUserInfoFromAuth0(userSession.accessToken)

                if(userInfo.email!=System.getenv("USER_EMAIL")) { login(call) }
                else { call.respondFile(File("${Roots.WS}silver.html")) }

            } else { login(call) }
        }

        get("/$SILVER/ws_values/{value}") {

            val userSession: Session? = call.sessions.get()
            val value = call.parameters["value"]

            if (userSession != null) {

                val userInfo = getUserInfoFromAuth0(userSession.accessToken)

                if(userInfo.email==System.getenv("USER_EMAIL")) {

                    val result = getValues(value)
                    call.respondText(result)
                }

                else { login(call) }

            } else { login(call) }
        }
    }
}

suspend fun login(call: ApplicationCall) {

    val redirectUrl = URLBuilder("$DOMAIN$SILVER/login").run {
        parameters.append("redirectUrl", call.request.uri)
        build()
    }

    call.respondRedirect(redirectUrl)
}

fun getValues(value: String?): String {

    return when(value) {

        "ada" -> File("${Roots.PY}ada_result.txt").readText()
        else  -> File("${Roots.PY}result.txt").readText()
    }
}
