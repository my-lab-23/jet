package com.example.jalaspot.ui

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Screen4(applicationContext: Context) {
    val s = saved()
    println(s)

    AndroidView(factory = {
        WebView(applicationContext).apply {
            webViewClient = WebViewClient()
            loadData(s!!, "text/html", "base64")
        }
    })
}
