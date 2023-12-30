package com.example

import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class AlbertoTest {

    @Test
    fun `Ping test`() {
        assertEquals(Response(OK).body("pong"), app(Request(GET, "/ping")))
    }

    @Test
    fun `Upload test`() {
        val filePath = Paths.get("/home/ema/sesto/Alberto/python/file.jpg")
        val fileBytes = Files.readAllBytes(filePath) // legge i byte del file di test
        val fileContentBase64 = Base64.getEncoder().encodeToString(fileBytes) // converte i byte in una stringa Base64
        val body = org.http4k.core.Body(fileContentBase64) // crea un oggetto Body dalla stringa Base64
        val request = Request(Method.POST, "/upload").body(body) // imposta il corpo della richiesta
        val expectedResponse = Response(OK).body("File ricevuto con successo!")
        assertEquals(expectedResponse, app(request))
    }
}
