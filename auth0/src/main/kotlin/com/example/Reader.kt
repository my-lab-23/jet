package com.example

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import java.io.File
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun Application.reader() {

    routing {

        // Static plugin. Try to access `/static/index.html`
        staticFiles("/other", File("./ws"))

        get("/other/reader") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession != null) {

                val userInfo = UserInfo.getUserInfoFromAuth0(userSession.accessToken)
                val diff = f(userInfo)

                if(diff > 300_000) {

                    val redirectUrl = URLBuilder("http://localhost:9090/other/login").run {
                        parameters.append("redirectUrl", call.request.uri)
                        build()
                    }

                    call.respondRedirect(redirectUrl)

                } else {

                    call.respondFile(File("$ws/reader.html"))
                }

            } else {

                val redirectUrl = URLBuilder("http://localhost:9090/other/login").run {
                    parameters.append("redirectUrl", call.request.uri)
                    build()
                }

                call.respondRedirect(redirectUrl)
            }
        }

        get("/other/reader/last/{n}") {

            val userSession: UserSession? = call.sessions.get()

            if (userSession != null) {

                val userInfo = UserInfo.getUserInfoFromAuth0(userSession.accessToken)
                val diff = f(userInfo)

                if(diff > 300_000) {

                    call.respond("You are not allowed to read the messages.")

                } else if(userInfo.email == System.getenv("USER_EMAIL")) {

                    val n = call.parameters["n"]?.toInt() ?: 10
                    val last: String = getLast(n)
                    call.respond(last)

                } else {

                    call.respond("You are not allowed to read the messages.")
                }

            } else {

                call.respond("You are not allowed to read the messages.")
            }
        }
    }
}

val client = HttpClient(CIO)

suspend fun getLast(n: Int): String {

    return client
        .get("http://0.0.0.0:9042/microexpo/searchLastNMsg/$n").body()
}

fun dateToTimestamp(dateString: String): Long {

    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val dt = OffsetDateTime.parse(dateString, formatter)
    return dt.toEpochSecond()*1000
}

fun f(userInfo: User): Long {

    val updatedAt = userInfo.updatedAt

    val now = System.currentTimeMillis()
    val updatedAtTimestamp = dateToTimestamp(updatedAt)
    return now - updatedAtTimestamp
}
