package com.example.stanza.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun StartScreen(switch: NavHostController) {

    // Column with padding
    Column(
        Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MyButton(switch, 1, "Nuovo contatto")
        Spacer(modifier = Modifier.padding(8.dp))
        MyButton(switch, 2, "Lista contatti")
    }
}
