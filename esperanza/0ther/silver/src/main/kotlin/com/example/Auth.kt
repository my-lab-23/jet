package com.example

import com.example.Roots.DOMAIN
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.auth(authClient: HttpClient = client) {

    install(Sessions) {

        cookie<Session>("user_session") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    //

    val redirects = mutableMapOf<String, String>()

    install(Authentication) {

        oauth("auth-oauth-auth0") {

            urlProvider = { "$DOMAIN$SILVER/callback" }

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
                            .request.queryParameters["redirectUrl"] ?: "/silver/post-login"
                    }
                )
            }

            client = authClient
        }
    }

    //

    routing {

        authenticate("auth-oauth-auth0") {

            get("/$SILVER/login") {}

            get("/$SILVER/callback") {

                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                call.sessions.set(Session(principal!!.accessToken))
                val redirect = redirects[principal.state!!]
                call.respondRedirect(redirect!!)
            }
        }

        get("/$SILVER/logout") {

            val url = "https://dev-zqz-kev4.eu.auth0.com/v2/logout?client_id=" +
                    System.getenv("AUTH0_CLIENT_ID")

            call.sessions.clear<Session>()
            client.get(url)

            call.respondRedirect("/$SILVER/post-logout")
        }
    }
}
