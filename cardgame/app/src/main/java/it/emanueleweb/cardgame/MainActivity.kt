package it.emanueleweb.cardgame

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import it.emanueleweb.cardgame.ui.GameBoard
import it.emanueleweb.cardgame.ui.theme.CardgameTheme
import it.emanueleweb.cardgame.logic.game
import it.emanueleweb.cardgame.ui.VerticalGameBoard
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CardgameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val check = rememberSaveable { mutableStateOf(0) }

                    if(check.value==0) {
                        thread { game() }
                    }

                    check.value = 1

                    val orientation = applicationContext.resources.configuration.orientation

                    if(orientation==Configuration.ORIENTATION_LANDSCAPE) {
                        GameBoard()
                    } else {
                        VerticalGameBoard()
                    }
                }
            }
        }
    }
}
