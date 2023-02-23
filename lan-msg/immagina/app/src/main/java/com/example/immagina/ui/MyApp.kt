package com.example.immagina.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.immagina.MyApplication
import com.example.immagina.Roads
import com.example.immagina.Sea
import com.example.immagina.halt
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun MyApp(navController: NavHostController) {

    val context = MyApplication.appContext
    val roads = Intent(context, Roads::class.java)
    val sea = Intent(context, Sea::class.java)
    val coroutineScope = rememberCoroutineScope()

    roads.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    sea.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).fillMaxSize()
    ) {
        Button(onClick = {
            var url = ""
            val c = coroutineScope.async { url = com.example.immagina.url("roads") }
            coroutineScope.launch {
                c.await()
                roads.putExtra("url", url)
                context.startActivity(roads)
            }
        }) {
            Text("ROADS", fontSize = 40.sp)
        }

        Button(onClick = {
            var url = ""
            val c = coroutineScope.async { url = com.example.immagina.url("sea") }
            coroutineScope.launch {
                c.await()
                sea.putExtra("url", url)
                context.startActivity(sea)
            }
        }) {
            Text("SEA", fontSize = 40.sp)
        }

        Button(onClick = {
            navController.navigate("circle")
        }) {
            Text("CIRCLE", fontSize = 40.sp)
        }

        Button(onClick = {
            coroutineScope.launch { halt() }
        }) {
            Text("HALT", fontSize = 40.sp)
        }
    }
}
