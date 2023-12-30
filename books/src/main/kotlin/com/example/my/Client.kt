package com.example.my

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object Client {

    private val client = HttpClient()

    suspend fun fetchAuthorData(id: String): String? {
        val url = "https://openlibrary.org/search.json?author=$id&sort=new&fields=title,key&lang=it"
        return fetchData(url)
    }

    suspend fun fetchComposerData(id: String, type: String): String? {
        val url = "https://api.openopus.org/work/list/composer/$id/genre/$type.json"
        return fetchData(url)
    }

    private suspend fun fetchData(url: String): String? {

        var result: String? = null

        try {
            val response: HttpResponse = client.get(url)
            result = response.bodyAsText()
        } catch (e: Exception) {
            println("Errore durante il recupero dei dati: ${e.message}")
        } finally {
            //client.close()
        }
        return result
    }
}
