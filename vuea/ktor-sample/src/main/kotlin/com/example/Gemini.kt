package com.example.my

import com.example.Client
import com.example.InputData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

data class TextData(val text: String)

@Serializable
data class RequestData(val inputJson: String, val name: String)

fun Application.configureGemini() {

    routing {
        post("/ktest/gemini") {
            val gson = Gson()
            val input = gson.fromJson(call.receiveText(), InputData::class.java)
            val response = Client.send(input.inputString)

            println("--- --- --- --- ---")
            println(response)

            call.respondText(response)
        }

        post("/ktest/bsky") {
            val gson = GsonBuilder().setPrettyPrinting().create()
            try {
                // Ricevi il JSON dal client
                val requestData = call.receive<RequestData>()

                val inputJson = requestData.inputJson
                val name = requestData.name

                // --- --- ---

                val responseJson = sendGemini(inputJson, name)

                // --- --- ---

                // Stampa il JSON in formato "pretty"
                // println(gson.toJson(gson.fromJson(inputJson, Any::class.java)))

                // Converte il JSON in una lista di oggetti TextData
                // val textType = object : TypeToken<List<TextData>>() {}.type
                // val texts: List<TextData> = gson.fromJson(inputJson, textType)

                // Creazione di una risposta
                // val responseJson = gson.toJson(
                //    mapOf("status" to "success", "message" to "Dati ricevuti con successo",
                //        "count" to texts.size))

                // Rispondi al client con il JSON
                call.respondText(responseJson, contentType = ContentType.Application.Json)
            } catch (e: Exception) {
                e.printStackTrace()
                // In caso di errore, rispondi con un messaggio di errore
                val errorJson = gson.toJson(
                    mapOf("status" to "error", "message" to "Errore durante l'elaborazione dei dati"))
                call.respondText(
                    errorJson, contentType = ContentType.Application.Json,
                    status = HttpStatusCode.BadRequest)
            }
        }
    }
}

suspend fun sendGemini(json: String, name: String): String {
    val answer = "// Riassumi i testi presenti nel json ed estrai il sentiment. " +
            "Rispondi in italiano. " +
            "All'inizio della risposta metti un intero da 0 a 100 che esprima il sentiment. " +
            "Mettilo con precisione proprio all'inizio, perché deve essere usato per un'analisi " +
            "automatica."
    // val n = Random.nextInt(0, 101).toString()
    val response = Client.send(json + answer)

    // Creare un oggetto JSON da json+answer+response
    val gson = GsonBuilder().setPrettyPrinting().create()
    val jsonObject = JsonObject().apply {
        addProperty("input", json)
        addProperty("answer", answer)
        addProperty("response", response)
    }

    // Convertire l'oggetto JSON in una stringa formattata
    val jsonString = gson.toJson(jsonObject)

    // Generare un nome di file univoco basato sul timestamp
    val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
    val fileName = "${name}_$timestamp.json"

    // Scrivere il JSON in un file
    val file = File("/home/ema/Documenti/Bsky/sentiment/$fileName")
    file.parentFile.mkdirs()  // Creare le cartelle necessarie se non esistono
    file.writeText(jsonString)
    return jsonString
}
