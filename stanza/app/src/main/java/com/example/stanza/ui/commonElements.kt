package com.example.stanza.ui

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MyButton(switch: NavController, i: Int, s: String) {

    Button(onClick = {
        when(i) {
            0 -> switch.navigate("startScreen")
            1 -> switch.navigate("inputScreen")
            2 -> switch.navigate("outputScreen")
        }
    },
        modifier = Modifier.defaultMinSize(400.dp)
    ) {
        Text(s, fontSize = 40.sp)
    }
}
