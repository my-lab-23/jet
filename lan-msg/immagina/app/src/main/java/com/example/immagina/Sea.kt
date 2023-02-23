package com.example.immagina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.immagina.ui.MyImage

class Sea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = intent.extras
        val url = b?.getString("url") ?: ""

        setContent {

            MyImage(url)
        }
    }
}
