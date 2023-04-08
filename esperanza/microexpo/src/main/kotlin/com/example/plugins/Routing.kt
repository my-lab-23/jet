package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/microexpo/searchLastNMsg/{n}") {

            val n = call.parameters["n"]?.toInt() ?: 5
            val response = MyDB.searchLastNMsgT(n)
            call.respondText(response)
        }
    }
}
