package com.example

import com.example.UserInfo.getUserInfoFromAuth0
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.birthday() {

    routing {

        get("/other/birthday/{name}/{surname}/{access_token}") {

            val name = call.parameters["name"]
            val surname = call.parameters["surname"]
            val accessToken = call.parameters["access_token"]

            if(accessToken==null) {

                call.respondText("Accesso negato (manca il token).")

            } else {

                val userInfo = getUserInfoFromAuth0(accessToken)

                if(userInfo.email==System.getenv("USER_EMAIL")) {

                    val process = ProcessBuilder("/usr/bin/python3.9",
                        "/home/ktor/python/birthday.py", name, surname).start()
                    // Verifica se il processo è terminato
                    process.waitFor()
                    // Leggi file /home/ema/result.txt
                    val file = File("/home/ktor/python/result.txt")
                    val result = file.readText()
                    // Invia risultato
                    call.respondText(result)

                } else { call.respondText("Accesso negato.") }
            }
        }
    }
}
