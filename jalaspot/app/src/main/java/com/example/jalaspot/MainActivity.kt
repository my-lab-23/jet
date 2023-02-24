package com.example.jalaspot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jalaspot.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "myApp") {
                composable("myApp") { MyApp(navController) }
                composable("screen1") { Screen1() }
                composable("screen2") { Screen2("load") }
                composable("screen3") { Screen3("save") }
                composable("screen4") { Screen4() }
            }
        }
    }
}
