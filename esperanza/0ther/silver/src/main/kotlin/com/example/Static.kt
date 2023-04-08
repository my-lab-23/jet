package com.example

import com.example.Roots.img
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.static() {

    routing {

        static("/silver/img") {

            staticRootFolder = File(img)
            files(".")
        }
    }
}
