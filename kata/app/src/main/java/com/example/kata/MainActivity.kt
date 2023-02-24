package com.example.kata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kata.ui.MyApp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyData.listApp.sortBy { it.name }

        setContent {

            //

            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )

                // setStatusBarColor() and setNavigationBarColor() also exist

                onDispose {}
            }

            //

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "myApp/{switch}") {
                composable("myApp/{switch}") {
                    val switch = it.arguments?.getString("switch") ?: "app"
                    MyApp(navController, switch)
                }
            }
        }
    }
}

fun runPackage(packageName: String) {

    val context = MyApplication.appContext

    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    context.startActivity(intent)
}
