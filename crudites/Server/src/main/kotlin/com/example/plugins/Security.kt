package com.example.plugins

import dev.forst.ktor.apikey.apiKey
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.Principal

fun Application.configureSecurity() {

    val expectedApiKey = System.getenv("CRUDITES_API_KEY")

    data class AppPrincipal(val key: String) : Principal

    install(Authentication) {
        apiKey {
            validate { keyFromHeader ->
                keyFromHeader
                    .takeIf { it == expectedApiKey }
                    ?.let { AppPrincipal(it) }
            }
        }
    }
}
