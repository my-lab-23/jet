package com.example.plugins

import com.example.MyDB
import com.example.MySerialization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.routing() {

    routing {

        authenticate {

            post("/esp32/signalstrength") {

                val jsonString = call.receive<String>()
                common(call, jsonString)
                MySerialization.deserializeSignal(jsonString)
            }

            post("/esp32/coordinates") {

                val jsonString = call.receive<String>()
                common(call, jsonString)
                MySerialization.deserializeCoord(jsonString)
            }

            get("/esp32/searchLastNSignals/{n}") {

                val n = call.parameters["n"]?.toInt() ?: 5
                val response = MyDB.getLastNSignals(n).joinToString("\n")
                call.respondText(response)
            }

            get("/esp32/searchLastNCoords/{n}") {

                val n = call.parameters["n"]?.toInt() ?: 5
                val response = MyDB.getLastNCoords(n).joinToString("\n")
                call.respondText(response)
            }

            get("/esp32/resetSignals") {

                transaction { MyDB.resetTableSignals() }
                call.respond(HttpStatusCode.OK)
            }

            get("/esp32/resetCoords") {

                transaction { MyDB.resetTableCoords() }
                call.respond(HttpStatusCode.OK)
            }

            get("/esp32/expectedTime") {

                val eT: Double = transaction { MyDB.expectedTime() }
                call.respondText(eT.toString())
            }

            get("/esp32/test") {

                call.respondText("xyz-123-v3f\n")
            }
        }
    }
}

suspend fun common(call: ApplicationCall, jsonString: String) {

    call.respond(HttpStatusCode.OK)
    println(jsonString)
}
