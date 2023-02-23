package com.example.immagina

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.IOException

val client = HttpClient(CIO)

suspend fun url(key: String): String {
    return try {
        val address = "http://192.168.1.23:8080/img/$key"
        val response: HttpResponse = client.get(address)
        response.bodyAsText()
    } catch (e: IOException) {
        "Error!"
    }
}

suspend fun halt(): String {
    return try {
        val address = "http://192.168.1.23:8080/shutdown"
        client.get(address)
        "OK"
    } catch (e: IOException) {
        "Error!"
    }
}
