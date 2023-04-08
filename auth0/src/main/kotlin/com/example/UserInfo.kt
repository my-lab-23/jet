package com.example

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class User(
    @SerialName("sub") val sub: String = "",
    @SerialName("given_name") val givenName: String = "",
    @SerialName("family_name") val familyName: String = "",
    @SerialName("nickname") val nickname: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("picture") val picture: String = "",
    @SerialName("locale") val locale: String = "",
    @SerialName("updated_at") val updatedAt: String = "0000-01-01T00:00:00Z",
    @SerialName("email") val email: String = "",
    @SerialName("email_verified") val emailVerified: Boolean = false
)

object UserInfo {

    private const val url = "https://dev-zqz-kev4.eu.auth0.com/userinfo"
    private val client = HttpClient()

    suspend fun getUserInfoFromAuth0(token: String): User {

        val response: String = client.get(url) {
            header("Authorization", "Bearer $token")
        }.bodyAsText()

        return try {

            Json.decodeFromString(response)

        } catch (e: Exception) {

            e.printStackTrace()
            User("", "", "", "", "", "", "", "", "", false)
        }
    }
}
