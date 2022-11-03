package com.example.scrivi

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.net.ConnectException
import java.net.NoRouteToHostException

val client = HttpClient(CIO)

suspend fun isConnected(address: String): Boolean {
    try {
        val response: HttpResponse = client.get(address)
        println("--- --- ---:" + response.status)
        if(response.bodyAsText()=="OK!") return true
    } catch (e: ClientRequestException) {
        println("--- --- ---:" + "ClientRequestException")
        return false
    } catch (e: ServerResponseException) {
        println("--- --- ---:" + "ServerResponseException")
        return false
    } catch (e: ConnectException) {
        println("--- --- ---:" + "ConnectException")
        return false
    } catch (e: NoRouteToHostException) {
        println("--- --- ---:" + "NoRouteToHostException")
        return false
    }

    return false
}

suspend fun invia(msg: String): Boolean {

    println(msg)

    // Example: http://192.168.1.36:8080/msg/Viva%20la%20mamma%20!

    val address = "http://192.168.1.23:8080"
    val addressMsg = "$address/msg/$msg|*|".replace(" ", "%20").replace("\n", "%20")

    try {
        client.get(addressMsg)
    } catch (e: ClientRequestException) {
        println("--- --- ---:" + "ClientRequestException")
        return false
    } catch (e: ServerResponseException) {
        println("--- --- ---:" + "ServerResponseException")
        return false
    } catch (e: ConnectException) {
        println("--- --- ---:" + "ConnectException")
        return false
    } catch (e: NoRouteToHostException) {
        println("--- --- ---:" + "NoRouteToHostException")
        return false
    }

    return false
}
