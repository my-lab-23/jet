package com.example.my

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

val messages = mutableListOf<String>()
val file = File("/home/ema/Documenti/messages.txt")

fun Application.configureMy() {

    routing {

        staticFiles("/", File("files"))

        // GET

        get("/my") {

            // Finché sono pochi...
            messages.clear()
            messages.addAll(file.readLines())
            val data = mapOf("messages" to messages)
            val html = myCreateHTML("files/lista.hbs", data)
            call.respondText(html, ContentType.Text.Html)
        }

        // POST

        post("/my/post/aggiungi") {

            val value = call.receive<String>()
            val encoded = value.split("=")[1]

            val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

            val msg = toHTML(decoded)

            if(msg!="default") {
                val a = ::aggiungiNewlineSeNecessario
                messages.add(a(msg))
                file.appendText(a(msg))
            }

            call.respondRedirect("/my")
        }

        post("/my/post/elimina") {

            val value = call.receive<String>() // ricevi la stringa
            val regex = Regex("\"message\":\"(.*?)\"") // crea l'espressione regolare
            val matchResult = regex.find(value) // applica l'espressione regolare alla stringa
            val msg = matchResult?.groups?.get(1)?.value // estrai il messaggio
            val msgHTML = toHTML(msg!!)
            messages.remove(msgHTML)
            file.writeText(messages.joinToString("\n") + "\n")
            call.respondRedirect("/my")
        }
    }
}

fun myCreateHTML(path: String, data: Map<String, List<String>>): String {

    val handlebars = Handlebars()
    val hbs = File(path).readText()
    val template: Template = handlebars.compileInline(hbs)
    return template.apply(data)
}

fun isLink(stringa: String): Boolean {
    val regex = Regex("""^(https?|ftp)://[^\s/$.?#].\S*$""")
    return regex.matches(stringa)
}

fun linkToHtml(link: String, text: String): String {
    return """<p><a class="message" href="$link">$text</a></p>"""
}

fun toHTML(s: String): String {

    val check = isLink(s)

    val msg = if(!check) {
        """<p class="message">$s</p>"""
    } else {
        linkToHtml(s, s)
    }

    return msg
}

fun aggiungiNewlineSeNecessario(input: String): String {
    return if (input.endsWith("\n")) {
        input
    } else {
        "$input\n"
    }
}
