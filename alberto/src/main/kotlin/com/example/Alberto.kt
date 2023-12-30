package com.example

import com.beust.klaxon.Klaxon
import com.example.formats.KlaxonMessage
import com.example.formats.klaxonMessageLens
import com.example.models.ImageViewModel
import com.example.models.MultiImageViewModel
import org.http4k.core.*
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.ResourceLoader.Companion.Directory
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.viewModel
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest
import java.util.*

val app: HttpHandler = routes(

    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    //

    "/upload/image" bind Method.POST to { req: Request ->

        val fileContentBase64 = req.bodyString()
        val fileBytes = Base64.getDecoder().decode(fileContentBase64)

        val responseMessage = when {

            isJPEG(fileBytes) -> {
                writeImage(fileBytes)
                "File ricevuto con successo!"
            }

            else -> "Il file inviato non è un'immagine JPEG."
        }

        Response(OK).body(responseMessage)
    },

    "/upload/json" bind Method.POST to { req: Request ->

        val klaxonMessage = klaxonMessageLens(req)
        writeJson(klaxonMessage)
        Response(OK).body("JSON ricevuto con successo!")
    },

    //

    "/links" bind GET to {

        val renderer = HandlebarsTemplates().CachingClasspath()
        val view = Body.viewModel(renderer, TEXT_HTML).toLens()

        val file = File("output.json")
        val fileLines = file.readLines()

        val json = parseJsonLines(fileLines)
        val multiViewModel = createViewModels(json)

        Response(OK).with(view of multiViewModel)
    },

    //

    "/static" bind static(Directory("./"))
)

//

fun isJPEG(file: ByteArray): Boolean {

    // L'intestazione di un file JPEG inizia con i byte 0xFF, 0xD8, 0xFF
    val jpegHeader = byteArrayOf(-1, -40, -1)
    return file.sliceArray(0..2).contentEquals(jpegHeader)
}

fun sha256Hash(bytes: ByteArray): String {

    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(bytes)
    return hash.joinToString("") { "%02x".format(it) }
}

fun writeImage(fileBytes: ByteArray) {

    val fileName = sha256Hash(fileBytes)
    val path = Paths.get("$fileName.jpg")
    Files.write(path, fileBytes)
}

fun writeJson(klaxonMessage: KlaxonMessage) {

    val json = mutableListOf(klaxonMessage)
    val klaxon = Klaxon()
    val jsonString = klaxon.toJsonString(json)
    FileWriter("output.json", true).use { it.write("$jsonString\n") }
}

//

fun parseJsonLines(fileLines: List<String>): MutableList<KlaxonMessage> {

    val klaxon = Klaxon()
    val json = mutableListOf<KlaxonMessage>()

    fileLines.forEach { line ->

        val trimmedLine = line.trimStart('[').trimEnd(']')
        val message: KlaxonMessage? = klaxon.parse<KlaxonMessage>(trimmedLine)

        if (message != null) {
            json.add(message)
        }
    }

    return json
}

fun createViewModels(json: MutableList<KlaxonMessage>): MultiImageViewModel {

    val viewModels = json.map { ImageViewModel(it.sha256, it.titolo, it.descrizione) }
    return MultiImageViewModel(viewModels)
}

//

fun main() {

    val printingApp: HttpHandler = PrintRequest().then(app)
    val server = printingApp.asServer(Jetty(9000)).start()
    println("Server started on " + server.port())
}
