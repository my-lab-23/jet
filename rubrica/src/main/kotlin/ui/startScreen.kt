package ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun startScreen(switch: MutableState<Int>) {

    // Column with padding
    Column(
        Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        myButton(switch, 1, "Nuovo contatto")
        Spacer(modifier = Modifier.padding(8.dp))
        myButton(switch, 2, "Lista contatti")
    }
}
