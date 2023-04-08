package it.emanueleweb.prenocomp.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Grazie(navController: NavHostController) {

    Button(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(16.dp),

        onClick = {
            navController.navigate("login")
        },
    ) {
        Text("Grazie!")
    }
}
