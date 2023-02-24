package com.example.kata.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kata.MyData
import com.example.kata.R
import com.example.kata.ui.theme.KataTheme

@Composable
fun MyApp(navController: NavHostController, switch: String) {

    val configuration = LocalConfiguration.current

    val fixedRowHeight = 100.dp
    val screenHeight = configuration.screenHeightDp.dp
    val rows = screenHeight / fixedRowHeight
    val ratio = rows - 1

    KataTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(ratio)
                ) {

                    Column(
                        Modifier.verticalScroll(rememberScrollState())
                    ) {

                        when (switch) {
                            "tech" -> {

                                MyTechCard(R.drawable.logo, "Kotlin Logo", "Kotlin", "JetBrains")
                                MyTechCard(R.drawable.logo, "Android Logo", "Android", "Google")

                            }
                            "other" -> {

                                Spacer(modifier = Modifier.height(40.dp))
                                Text("Under construction!", fontSize = 40.sp)

                            }
                            else -> {

                                MyData.listApp.forEach { app ->
                                    MyAppCard(app.name, app.description, app.packageName)
                                }
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color(0xFFABCDEF))
                ) {

                    MyButton("App", navController)
                    Spacer(modifier = Modifier.width(32.dp))
                    MyButton("Tech", navController)
                    Spacer(modifier = Modifier.width(32.dp))
                    MyButton("Other", navController)
                }
            }
        }
    }
}
