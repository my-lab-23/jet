package com.example.mummyandroidreader

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object MyHTTP {

    private val client = HttpClient(CIO)

    suspend fun sendToken(
        firstName: String = "Serena",
        lastName: String = "Rossi",
        accessToken: String): String {

        val url = "https://2desperados.it/other/birthday/$firstName/$lastName/$accessToken"
        return client.request(url).bodyAsText()
    }
}
