package com.example

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.security.cert.X509Certificate
import javax.net.ssl.*

object UserProfileService {

    // Modificato per restituire direttamente un oggetto UserProfile
    suspend fun getUserProfile(url: String, oauthCookie: String?): UserProfile {
        val (_, _, result) = url
            .httpGet()
            .header("Cookie" to "_oauth2_proxy=$oauthCookie")
            .awaitStringResponse()

        return parseUserProfile(result)
    }

    // Modificato per restituire un oggetto UserProfile anziché una mappa
    private fun parseUserProfile(response: String): UserProfile {
        val json = Json { ignoreUnknownKeys = true }

        return try {
            // Decodifica la risposta JSON in un oggetto UserProfile
            json.decodeFromString<UserProfile>(response)
        } catch (e: Exception) {
            println("Errore nel parsare il profilo utente: ${e.message}")

            // Restituisce un oggetto UserProfile con valori di fallback
            UserProfile(name = "Utente", email = "Non disponibile", address = "Non disponibile")
        }
    }
}

fun configureFuel() {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    FuelManager.instance.apply {
        socketFactory = sslContext.socketFactory
        hostnameVerifier = HostnameVerifier { _, _ -> true }
    }
}

@Serializable
data class UserProfile(
    val name: String? = null,
    val email: String? = null,
    val address: String? = null
)
