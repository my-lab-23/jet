package ui

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun myButton(switch: MutableState<Int>, i: Int, s: String) {

    Button(onClick = {
        switch.value = i
    },
        modifier = Modifier.defaultMinSize(400.dp)
    ) {
        Text(s, fontSize = 40.sp)
    }
}
