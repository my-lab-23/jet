package com.example.plugins

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import kotlin.random.Random

fun Application.configureRouting() {
    routing {

        get("/") {

            val data = generateMapAndString()
            val html = createHTML("files/index.hbs", data)
            call.respondText(html, ContentType.Text.Html)
        }

        //

        get("/get/msg/{msg?}") {

            val msg = call.parameters["msg"] ?: "default"
            val data = mapOf("message" to msg)
            val html = createHTML("files/msg.hbs", data)
            call.respondText(html, ContentType.Text.Html)
        }

        post("/post/msg") {

            val msg = call.receive<String>()
            val value = msg.split("=")[1]
            println("value: $value")
            call.respondRedirect("/get/msg/$value")
        }
    }
}

fun createHTML(path: String, data: Map<String, String>): String {

    val handlebars = Handlebars()
    val hbs = File(path).readText()
    val template: Template = handlebars.compileInline(hbs)
    return template.apply(data)
}

fun generateMapAndString(): Map<String, String> {

    val operations = listOf("+", "-", "*", "/")
    val numbers = List(4) { Random.nextInt(1, 10) }
    val ops = List(3) { operations.random() }

    val n5 = when (ops[0]) {
        "+" -> numbers[0] + numbers[1]
        "-" -> numbers[0] - numbers[1]
        "*" -> numbers[0] * numbers[1]
        "/" -> numbers[0] / numbers[1]
        else -> numbers[0]
    }.let { result ->
        when (ops[1]) {
            "+" -> result + numbers[2]
            "-" -> result - numbers[2]
            "*" -> result * numbers[2]
            "/" -> result / numbers[2]
            else -> result
        }
    }.let { result ->
        when (ops[2]) {
            "+" -> result + numbers[3]
            "-" -> result - numbers[3]
            "*" -> result * numbers[3]
            "/" -> result / numbers[3]
            else -> result
        }
    }

    val str = "${numbers[0]} ${ops[0]} ${numbers[1]} ${ops[1]} ${numbers[2]} ${ops[2]} ${numbers[3]}"

    return mapOf(
        "n1" to numbers[0].toString(),
        "n2" to numbers[1].toString(),
        "n3" to numbers[2].toString(),
        "n4" to numbers[3].toString(),
        "risultato" to n5.toString(),
        "formula" to str
    )
}
