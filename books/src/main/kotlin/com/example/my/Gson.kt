package com.example.my

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

data class JsonData(val numFound: Int)

fun getNumFound(jsonString: String): Int {
    val gson = Gson()
    val jsonElement = JsonParser.parseString(jsonString)
    val jsonData = gson.fromJson(jsonElement, JsonData::class.java)
    return jsonData.numFound
}

data class Document(val title: String, val key: String)
data class Input(val docs: List<Document>)

fun getTitlesAndKeys(input: String): List<Pair<String, String>> {
    val gson = Gson()
    val type = object : TypeToken<Input>() {}.type
    val data = gson.fromJson<Input>(input, type)
    return data.docs.map { Pair(it.title, it.key) }
}

fun estraiRows(json: String): Int {
    val gson = Gson()
    val jsonObject = gson.fromJson(json, JsonObject::class.java)
    val status = jsonObject.getAsJsonObject("status")
    return try { status.get("rows").asInt }
    catch(e: NullPointerException) { 0 }
}

data class Work(val title: String)
data class Response(val works: List<Work>)

fun extractTitles(json: String): List<String> {
    val gson = Gson()
    val type = object : TypeToken<Response>() {}.type
    val response: Response = gson.fromJson(json, type)
    return response.works.map { it.title }
}
