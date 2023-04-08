package it.emanueleweb.esperanza

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import it.emanueleweb.esperanza.ui.AnonymousEncouragementMessageScreen
import it.emanueleweb.esperanza.ui.theme.EsperanzaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsperanzaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFD9EAD3)
                ) {
                    AnonymousEncouragementMessageScreen { message ->

                        val msg = MyMessage(message)
                        val scope = CoroutineScope(Dispatchers.IO)

                        scope.launch { invia(msg.getJsonObject()) }
                    }
                }
            }
        }
    }
}
