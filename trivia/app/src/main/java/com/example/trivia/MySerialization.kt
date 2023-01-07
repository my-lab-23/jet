package com.example.trivia

import android.content.Context
import androidx.core.text.HtmlCompat
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject

@Serializable
private data class Trivia(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)

suspend fun loadJsonFile() {

    val fileData = url()
    save(fileData, "trivia.txt", Context.MODE_PRIVATE)
}

fun loadJsonArray(): JSONArray {

    val fileData = load("trivia.txt")
    return JSONObject(fileData).getJSONArray("results")
}

fun loadQuestion(index: Int): String {

    val jsonArray = loadJsonArray()
    val format = Json { ignoreUnknownKeys = true }
    val fromApi = format.decodeFromString<Trivia>(jsonArray[index].toString()).question
    return HtmlCompat.fromHtml(fromApi, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

fun loadCorrect(index: Int): String {

    val jsonArray = loadJsonArray()
    val format = Json { ignoreUnknownKeys = true }
    val fromApi = format.decodeFromString<Trivia>(jsonArray[index].toString()).correct_answer
    return HtmlCompat.fromHtml(fromApi, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

fun loadIncorrect(index: Int): List<String> {

    val jsonArray = loadJsonArray()
    val format = Json { ignoreUnknownKeys = true }
    val fromApi = format.decodeFromString<Trivia>(jsonArray[index].toString()).incorrect_answers

    //

    val list = mutableListOf<String>()

    for(fA in fromApi) {

        list.add(HtmlCompat.fromHtml(fA, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())
    }

    return list
}
