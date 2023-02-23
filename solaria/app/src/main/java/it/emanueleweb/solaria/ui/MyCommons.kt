package it.emanueleweb.solaria.ui

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.emanueleweb.solaria.MyData.searchLock

@Composable
fun MyButton(s: String, navController: NavHostController, search: MutableState<Int>) {

    OutlinedButton(
        onClick = {

            search.value = 0

            when (s) {

                "Luoghi" -> {
                    searchLock = "l"
                    navController.navigate("myEntity/luoghi")
                }

                "Attrattive" -> {
                    searchLock = "a"
                    navController.navigate("myEntity/attrattive")
                }

                "Eventi" -> {
                    searchLock = "e"
                    navController.navigate("myEntity/eventi")
                }
            }
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        border = BorderStroke(3.dp, Color.DarkGray),
        modifier = Modifier.defaultMinSize(100.dp).semantics { contentDescription = s }
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
