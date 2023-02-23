package it.emanueleweb.terremoti

import com.google.gson.JsonParser
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun myGeoJson(): MutableList<MyData> {

    val jsonStringList = load("data.geojson").toMutableList()
    val coordinates = mutableListOf<MyData>()

    jsonStringList.removeAt(0)
    jsonStringList.removeAt(jsonStringList.size-1)

    jsonStringList.forEach {

        val updatedString = it.substring(0, it.length - 1)
        val c = myGeoJsonString(updatedString)
        coordinates += c
    }

    return coordinates
}

data class MyData(

    val title: String,
    val longitude: Double,
    val latitude: Double,
    val date: String
)

fun myGeoJsonString(s: String): MyData {

    val parsedJson = JsonParser.parseString(s).asJsonObject

    val title = parsedJson.get("properties")
        .asJsonObject.get("title")
        .asString

    val longitude = parsedJson.get("geometry")
        .asJsonObject.get("coordinates")
        .asJsonArray.get(1)
        .asDouble

    val latitude = parsedJson.get("geometry")
        .asJsonObject.get("coordinates")
        .asJsonArray.get(0)
        .asDouble

    val milliseconds = parsedJson.get("properties")
        .asJsonObject.get("time")
        .asLong

    val date = convertMillisecondsToDate(milliseconds)

    return MyData(title, latitude, longitude, date)
}

fun load(s: String): List<String> {

    val fileContext = MyApplication.appContext
    val content = mutableListOf<String>()
    try {
        val input = fileContext.openFileInput(s)
        val reader = BufferedReader(InputStreamReader(input))
        reader.use {
            reader.forEachLine {
                content.add(it)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return content
}

fun save(inputText: String, fileName: String, mode: Int) {

    val fileContext = MyApplication.appContext
    try {
        val output = fileContext.openFileOutput(fileName, mode)
        val writer = BufferedWriter(OutputStreamWriter(output))
        writer.use {
            it.write(inputText)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

val client = HttpClient(CIO)

suspend fun url(): String {

    return try {

        val address = "https://earthquake.usgs.gov/fdsnws/event/1/query.geojson?" +
                "starttime=2022-01-13%2000:00:00&" +
                "maxlatitude=47.458&" +
                "minlatitude=36.633&" +
                "maxlongitude=18.545&" +
                "minlongitude=6.196&" +
                "orderby=time&" +
                "limit=30"

        val response: HttpResponse = client.get(address)
        response.bodyAsText()

    } catch (e: IOException) {
        "Error!"
    }
}

suspend fun query() {

    val scope = CoroutineScope(Dispatchers.IO)

    val c = scope.async {
        val data = url()
        save(data, "data.geojson", 0)
    }

    c.await()
}

fun convertMillisecondsToDate(milliseconds: Long): String {
    val date = Date(milliseconds)
    val format = SimpleDateFormat("dd-MM-yyyy")
    return format.format(date)
}
