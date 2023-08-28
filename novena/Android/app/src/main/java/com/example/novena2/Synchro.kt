package com.example.novena2

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

object Synchro {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val client = HttpClient(CIO)
    private val adr = "https://2desperados.it/novena/"

    private suspend fun invia(s: String): String {

        val serial = Aux.getSerial()

        val url = "${adr}write/$serial"

        return try {

            val response: HttpResponse = client.post(url) {
                setBody(s)
                header("X-Api-Key", apiKey)
            }

            response.bodyAsText()

        } catch (e: IOException) {

            "IOException"
        }
    }

    suspend fun syncroBackup() {

        val t = MyApplication.appContext
        saveBooleanArrayToSharedPreferences(t, greens, "my_boolean_array")

        val a = loadBooleanArrayFromSharedPreferences(t,
            "my_boolean_array", BooleanArray(9) { false } )
        Log.d("MyBool", a.joinToString("\n"))
        val s = JAux.convertString(a.joinToString("\n"))
        Log.d("MyBool", s)
        invia(s)
    }

    suspend fun read(): String {
        val url = "${adr}read"

        return try {
            val response: HttpResponse = client.get(url) {
                header("X-Api-Key", apiKey)
            }

            response.bodyAsText()
        } catch (e: IOException) {
            "IOException"
        }
    }

    private fun convertStringToBooleanArray(input: String): BooleanArray {
        val lines = input.trim().split("\n")
        val result = BooleanArray(lines.size)

        for (i in lines.indices) {
            result[i] = lines[i].trim().equals("green", ignoreCase = true)
        }

        return result
    }

    //

    @JvmStatic
    fun update() {

        val c = scope.launch {
            val s = read()
            Log.d("MyRead", s)
            if(s!="IOException") {
                Log.d("MyRead", s)
                val a = convertStringToBooleanArray(s)
                Log.d("MyArray", a.joinToString())
                val t = MyApplication.appContext
                saveBooleanArrayToSharedPreferences(t, a, "my_boolean_array")
            }
        }
        while(!c.isCompleted) { /* --- */ }

        Log.d("MySize", "${greens.size}")

        val t = MyApplication.appContext
        greens = loadBooleanArrayFromSharedPreferences(
            t,
            "my_boolean_array",
            BooleanArray(9) { false }
        )

        if(greens.size==1) greens = BooleanArray(9) { false }

        Log.d("MySize", "${greens.size}")

        isUpdate = if(isUpdate==-1) 1
        else -1
    }
}
