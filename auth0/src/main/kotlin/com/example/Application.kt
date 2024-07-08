package com.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit =
    io.ktor.server.cio.EngineMain.main(args)

//

fun Application.module() {

    main()
    reader()
    prenotazioni()
    postlog()
    birthday()
    testAuth()
}

//

val applicationHttpClient = HttpClient(CIO) {

    install(ContentNegotiation) {

        json(Json {
            //prettyPrint = true
            isLenient = true
        })
    }
}

//

fun Application.main(httpClient: HttpClient = applicationHttpClient) {

    install(Sessions) {

        cookie<UserSession>("user_session") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    val redirects = mutableMapOf<String, String>()

    install(Authentication) {

        oauth("auth-oauth-auth0") {

            urlProvider = { "http://localhost:9090/other/callback" }

            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "auth0",
                    authorizeUrl = "https://dev-zqz-kev4.eu.auth0.com/authorize?&prompt=login",
                    accessTokenUrl = "https://dev-zqz-kev4.eu.auth0.com/oauth/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("AUTH0_CLIENT_ID"),
                    clientSecret = System.getenv("AUTH0_CLIENT_SECRET"),
                    defaultScopes = listOf("openid", "profile", "email"),
                    onStateCreated = { call, state ->
                        redirects[state] = call
                            .request.queryParameters["redirectUrl"] ?: "/other/post-login"
                    }

                )
            }

            client = httpClient
        }
    }

    routing {

        authenticate("auth-oauth-auth0") {

            get("/other/login") {}

            get("/other/callback") {

                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                call.sessions.set(UserSession(principal!!.accessToken))
                val redirect = redirects[principal.state!!]
                call.respondRedirect(redirect!!)
            }
        }

        get("/other/logout") {

            val url = "https://dev-zqz-kev4.eu.auth0.com/v2/logout?client_id=" +
                    System.getenv("AUTH0_CLIENT_ID")

            call.sessions.clear<UserSession>()
            client.get(url)

            call.respondRedirect("/other/post-logout")
        }
    }
}

data class UserSession(val accessToken: String)
val ws = "/home/ema/Scrivania/Gradle/fine/k-out/auth0/ws"
