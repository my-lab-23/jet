package com.example.bibbia

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlinx.coroutines.*

suspend fun verse(): String {

    val url = ""

    val doc: Document = withContext(Dispatchers.IO) {
        try {
            Jsoup.connect(url).get()
        } catch (e: android.os.NetworkOnMainThreadException) {
            // Gestisci l'eccezione qui
        }
    } as Document

    val element = doc.select("span.v1").first()

    return element.text()
}
