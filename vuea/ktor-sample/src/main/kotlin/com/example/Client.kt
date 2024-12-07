package com.example

import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

data class InputData(val inputString: String)

object Client {

    private val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 120_000
            // connectTimeoutMillis = 30_000
            socketTimeoutMillis = 120_000
        }
    }

    private val gson = Gson()

    suspend fun send(s: String): String {

        val url = "http://localhost:3000/gemini"
        val data = InputData(s)
        val jsonData = gson.toJson(data)

        return try {
            val response: HttpResponse = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonData)
            }
            response.bodyAsText()

        } catch (e: SocketTimeoutException) {
            "TimeoutException: The request timed out"
        } catch (e: ConnectException) {
            "ConnectException: Failed to connect to the server"
        } catch (e: UnknownHostException) {
            "UnknownHostException: Unable to resolve host"
        } catch (e: IOException) {
            "IOException: An I/O error occurred - ${e.message}"
        } catch (e: Exception) {
            "Exception: An unexpected error occurred - ${e.message}"
        }
    }
}

suspend fun main() {
    val res = Client.send("Ciao!")
    println(res)
}
