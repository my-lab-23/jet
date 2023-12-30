package com.example.plugins

import com.example.my.*
import com.example.my.Maker.makeHTML0
import com.example.my.Maker.makeHTML1
import com.example.my.Maker.makeHTML2
import com.example.my.Maker.transformData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

fun Application.configureRouting() {
    routing {

        get("/books") {
            call.respondRedirect("/books/index.html")
        }

        // Static plugin. Try to access `/static/index.html`
        staticFiles("/books", File("./ws"))

        //

        var isDataReady = false // Variabile di stato

        get("/books/fetch") {

            launch(Dispatchers.IO) {
                
                for ((person, valore) in Authors) {
                    val response = Client.fetchAuthorData(valore) ?: "Errore!"
                    File("./data/$person.json").writeText(response)
                }

                for ((person, valore) in Composers) {
                    val response = Client.fetchComposerData(valore, "Recommended") ?: "Errore!"
                    File("./data/$person.json").writeText(response)
                }

                for ((person, valore) in Composers) {
                    val response = Client.fetchComposerData(valore, "all") ?: "Errore!"
                    File("./data/all$person.json").writeText(response)
                }

                // Aggiorna lo stato quando il job è completato
                isDataReady = true
            }

            // Mentre le operazioni di lunga durata sono in esecuzione,
            // reindirizza l'utente a una pagina di attesa
            call.respondText("""
            <html>
            <head>
                <meta http-equiv="refresh" content="5;url=/books/checkDataReady" />
            </head>
            <body>
                Attendere il caricamento dei dati...
            </body>
            </html>
            """.trimIndent(), ContentType.Text.Html)
        }

        get("/books/checkDataReady") {

            if (isDataReady) { // Controlla lo stato
                call.respondRedirect("/books/index.html")
            } else {
                call.respondText("""
                <html>
                <head>
                    <meta http-equiv="refresh" content="5;url=/books/checkDataReady" />
                </head>
                <body>
                    Attendere il caricamento dei dati...
                </body>
                </html>
                """.trimIndent(), ContentType.Text.Html)
            }
        }

        //

        get("/books/authors") {

            val dati = mutableMapOf<String, Int>()

            for ((autore) in Authors) {
                val json = File("data/$autore.json").readText()
                val numFound = getNumFound(json)
                dati[autore] = numFound
            }

            val html = makeHTML1("authors", dati) ?: "Errore!"

            call.respondText(html, ContentType.Text.Html)
        }

        get("/books/composers") {

            val dati = mutableMapOf<String, Pair<Int, Int>>()

            for ((composer) in Composers) {
                val json0 = File("data/$composer.json").readText()
                val numFound0 = estraiRows(json0)
                val json1 = File("data/all$composer.json").readText()
                val numFound1 = estraiRows(json1) // Può essere zero !!!
                dati[composer] = Pair(numFound0, numFound1)
            }

            val datiT = transformData(dati)
            val html = makeHTML0("composers", datiT) ?: "Errore!"

            call.respondText(html, ContentType.Text.Html)
        }

        //

        get("/books/books/{people}") {

            val autore = call.parameters["people"].toString()
            val json = File("data/$autore.json").readText()
            val titlesAndKeys = getTitlesAndKeys(json)
            val html = makeHTML2("books", titlesAndKeys) ?: "Errore!"
            call.respondText(html, ContentType.Text.Html)
        }

        get("/books/works/{people}") {

            val compositore = call.parameters["people"].toString()
            val json = File("data/$compositore.json").readText()
            val titles = extractTitles(json)
            val html = makeHTML2("works", titles) ?: "Errore!"
            call.respondText(html, ContentType.Text.Html)
        }

        //

        post("/books/search") {

            val parameters = call.receiveParameters()
            val composer = parameters["composers"]
            val message = parameters["message"].toString()
            val recommended = parameters["recommended"]
            val json = if(recommended==null) File("data/all$composer.json").readText()
            else File("data/$composer.json").readText()
            val titles = extractTitles(json)
            val matchingTitles = titles.filter { it.contains(message, ignoreCase = true) }
            val html = makeHTML2("search", matchingTitles) ?: "Errore!"
            call.respondText(html, ContentType.Text.Html)
        }
    }
}
