package it.emanueleweb.esperanza

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import java.io.IOException

val client = HttpClient(CIO)

suspend fun invia(jsonObject: JsonObject): HttpResponse {

    val response: HttpResponse = client.post("https://2desperados.it/examples/msg") {
        setBody(jsonObject.toString())
    }

    return response
}
