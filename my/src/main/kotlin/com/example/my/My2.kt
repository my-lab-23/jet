package com.example.my

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.random.Random

fun Application.configureMy2() {

    routing {

        staticFiles("/", File("files"))

        // GET

        get("/my2/{order}") {
            val order = call.parameters["order"].toString()
            val images = getFiles("files/my2/img", order)
            val pairedImages = images.chunked(2)
            val data = mapOf("images" to pairedImages)
            val html = my2CreateHTML("files/my2.hbs", data)
            call.respondText(html, ContentType.Text.Html)
        }
    }
}

fun my2CreateHTML(path: String, data: Map<String, List<List<String>>>): String {
    val handlebars = Handlebars()
    val hbs = File(path).readText()
    val template: Template = handlebars.compileInline(hbs)
    return template.apply(data)
}

fun getFiles(path: String, order: String): List<String> {

    val directory = Paths.get(path)

    val files = Files.list(directory)
        .filter { it.toString().endsWith(".jpeg") }
        .map { it.toFile() }
        .toList()

    files.forEach {

        println(it.name)
        println(it.lastModified())
    }

    return when (order) {
        "sorted" -> files.sortedBy { it.lastModified() }.map { it.name }
        "inversed" -> files.sortedByDescending { it.lastModified() }.map { it.name }
        "shuffled" -> files.shuffled(Random).map { it.name }
        else -> files.map { it.name }
    }
}
