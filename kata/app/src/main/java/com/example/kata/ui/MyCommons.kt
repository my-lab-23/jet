package com.example.kata.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MyButton(s: String, navController: NavHostController) {

    OutlinedButton(
        onClick = {

            when (s) {
                "App" -> navController.navigate("myApp/app")
                "Tech" -> navController.navigate("myApp/tech")
                "Other" -> navController.navigate("myApp/other")
            }
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        border = BorderStroke(3.dp, Color.DarkGray),
        modifier = Modifier.defaultMinSize(100.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                modifier = Modifier.size(30.dp),
                contentDescription = "Localized description",
                tint = Color.Red
            )

            Text(text = s)
        }
    }
}
