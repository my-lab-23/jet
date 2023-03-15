package com.example.scrivi

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import java.io.IOException

val client = HttpClient(CIO)

suspend fun isConnected(address: String): Boolean {
    return try {
        val response: HttpResponse = client.get(address)
        response.bodyAsText()=="OK!"
    } catch (e: IOException) {
        false
    }
}

suspend fun invia(jsonObject: JsonObject): Boolean {

    return try {

        client.submitForm(
            url = "http://192.168.1.23:8080/msg",
            formParameters = Parameters.build {
                append("msg", jsonObject.toString())
            }
        )

        true

    } catch (e: IOException) { false }
}
