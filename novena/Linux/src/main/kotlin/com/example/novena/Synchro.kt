package com.example.novena

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jnovena.WebSocketClientExample
import java.io.File
import java.io.IOException

object Synchro {

    private val client = HttpClient(CIO)
    private val adr = "https://2desperados.it/novena/"

    private suspend fun invia(s: String): String {

        val serial = Aux.getSerial()

        val url = "${adr}write/$serial"

        println(apiKey)

        return try {

            val response: HttpResponse = client.post(url) {
                setBody(s)
                header("X-Api-Key", apiKey)
            }

            response.bodyAsText()

        } catch (e: IOException) { "IOException" }
    }

    private fun readFileAsString(filePath: String): String {
        val file = File(filePath)
        return file.readText()
    }

    suspend fun synchroBackup(filePath: String) {

        val s = readFileAsString(filePath)
        val r = invia(s)
        println(r)
    }

    suspend fun read(): String {
        val url = "${adr}read"

        return try {
            val response: HttpResponse = client.get(url) {
                header("X-Api-Key", apiKey)
            }

            response.bodyAsText()
        } catch (e: IOException) {
            "IOException"
        }
    }

    fun formJava() { startUp() }
}
