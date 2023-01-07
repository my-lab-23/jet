package com.example.jalaspot.ui

import android.util.Base64
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jalaspot.MyHTTP.get
import com.example.jalaspot.R
import kotlinx.coroutines.*

@Composable
fun TopBar() {
    TopAppBar(title = {
        Text(
            text = "Jalaspot",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    })
}

@Composable
fun Img(i: Int = R.drawable.image, s: String, navController: NavHostController? = null) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { f(s, navController) },
        painter = painterResource(id = i),
        contentDescription = null
    )
}

fun f(s: String, navController: NavHostController? = null) {
    @Suppress("DUPLICATE_LABEL_IN_WHEN")
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
        val r = get("http://192.168.1.23:9000/$s")
        println(r)
    }
}

fun load(s: String) {
    scope.launch {
        val r = get("http://192.168.1.23:9000/load?label=$s")
        println(r)
    }
}

fun save(s: String) {
    scope.launch {
        val r = get("http://192.168.1.23:9000/save?label=$s")
        println(r)
    }
}

fun remove() {
    scope.launch {
        val r = get("http://192.168.1.23:9000/remove")
        println(r)
    }
}

fun dropbox() {
    scope.launch {
        val r = get("http://192.168.1.23:9000/dropbox")
        println(r)
    }
}

@Composable
fun F(
    ab: List<Int>,
    stringList: List<String>,
    intList: List<Int>,
    navController: NavHostController? = null,
) {

    var i = 0

    repeat(ab[0]) {
        Row(modifier = Modifier.padding(20.dp)) {
            repeat(ab[1]) {
                Box(Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Img(intList[i], stringList[i], navController)
                        Text(stringList[i], fontSize = 20.sp, color = Color.White)
                    }
                }
                i += 1
            }
        }
    }
}

@Composable
fun MyScrivi(s: String) {

    var text by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
        )

        val coroutineScope = rememberCoroutineScope()

        Button(onClick = {
            val msg = text
            if(s=="load") { coroutineScope.launch { load(msg) } }
            if(s=="save") { coroutineScope.launch { save(msg) } }
            text = ""
        }) {
            Text("INVIA", fontSize = 40.sp)
        }
    }
}

//

fun saved() = runBlocking {
    var r = ""

    val c = async {
        r = get("http://192.168.1.23:9000/saved")
        println(r)
    }

    c.await()
    return@runBlocking message(r)
}

fun message(message: String): String? {
    return Base64.encodeToString(message.toByteArray(), Base64.NO_PADDING)
}
