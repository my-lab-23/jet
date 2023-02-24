package com.example.jalaspot

import android.util.Base64
import androidx.navigation.NavHostController
import kotlinx.coroutines.*

fun executeButtonCommand(s: String, navController: NavHostController? = null) {
    when (s) {
        "preferiti" -> navController!!.navigate("screen1")
        "load" -> navController!!.navigate("screen2")
        "save" -> navController!!.navigate("screen3")
        "saved" -> navController!!.navigate("screen4")
        "dropbox" -> dropbox()
        "remove" -> remove()
        "haydn" -> preferiti("haydn")
        "mozart" -> preferiti("mozart")
        "invernizzi" -> preferiti("invernizzi")
        "faust" -> preferiti("faust")
        "haydn2032" -> preferiti("haydn2032")
    }
}

val scope = CoroutineScope(Dispatchers.IO)

fun preferiti(s: String) {
    scope.launch {
        MyHTTP.get("http://192.168.1.23:9000/$s")
    }
}

fun load(s: String) {
    scope.launch {
        MyHTTP.get("http://192.168.1.23:9000/load?label=$s")
    }
}

fun save(s: String) {
    scope.launch {
        MyHTTP.get("http://192.168.1.23:9000/save?label=$s")
    }
}

fun remove() {
    scope.launch {
        MyHTTP.get("http://192.168.1.23:9000/remove")
    }
}

fun dropbox() {
    scope.launch {
        MyHTTP.get("http://192.168.1.23:9000/dropbox")
    }
}

//

suspend fun saved(): String {

    val c = scope.async {
        MyHTTP.get("http://192.168.1.23:9000/saved")
    }

    val r = c.await()
    return message(r)
}

fun message(message: String): String {
    return Base64.encodeToString(message.toByteArray(), Base64.NO_PADDING)
}
