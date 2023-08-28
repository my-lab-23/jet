package com.example.trentadue

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.errors.IOException

object MyClient {

    private val client = HttpClient(CIO)
    private val url = "https://2desperados.it/esp32/searchLastNCoords/1"

    suspend fun read(): String {

        return try {
            val response: HttpResponse = client.get(url) {
                header("X-Api-Key", apiKey)
            }

            response.bodyAsText()

        } catch (e: IOException) {

            println(e.stackTrace)
            "IOException"
        }
    }
}
