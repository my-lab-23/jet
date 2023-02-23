package it.emanueleweb.solaria.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyAbout() {

    val text0 = "App dimostrativa per luoghi, attrattive ed eventi inventati " +
                "da ChatGPT ed illustrati da DALL-E."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = text0, fontSize = 20.sp)
    }
}
