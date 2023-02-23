package it.emanueleweb.solaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import it.emanueleweb.solaria.ui.MyAbout
import it.emanueleweb.solaria.ui.MyInfo
import it.emanueleweb.solaria.ui.MyEntity
import it.emanueleweb.solaria.ui.theme.SolariaTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {

            MyIO.NetworkIO.loadData()

            setContent {
                SolariaTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {

                        SetBarColor()

                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = "myEntity/{switch}"
                        ) {
                            composable("myEntity/{switch}") {
                                val switch = it.arguments?.getString("switch") ?: "luoghi"
                                MyEntity(navController, switch)
                            }
                            composable("myInfo/{id}") {
                                val id = it.arguments?.getString("id")
                                if (id != null) { MyInfo(id) }
                            }
                            composable("myAbout") { MyAbout() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetBarColor() {

    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true

    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = useDarkIcons
        )

        // setStatusBarColor() and setNavigationBarColor() also exist

        onDispose {}
    }
}
