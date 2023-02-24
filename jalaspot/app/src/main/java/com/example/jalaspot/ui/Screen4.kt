package com.example.jalaspot.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jalaspot.saved

@Composable
fun Screen4() {

    val s = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = "saved") {
            s.value = saved()
    }

    if(s.value != "") {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    loadData(s.value, "text/html", "base64")
                }
            }
        )
    }
}
