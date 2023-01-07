package com.example.jalaspot

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.io.IOException

object MyHTTP {

    private val client = HttpClient()

    suspend fun get(url: String): String {
        return try {
            val r = client.get(url)
            return r.body()
        } catch (e: IOException) {
            "IOException"
        }
    }
}
