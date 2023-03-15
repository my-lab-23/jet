package com.example.scrivi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.scrivi.ui.MyScrivi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //

        val msg = MyMessage("Ciao!")
        println(msg.toJsonString())

        //

        setContent { MyScrivi() }
    }
}
