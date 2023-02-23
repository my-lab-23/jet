package it.emanueleweb.solaria.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.emanueleweb.solaria.MyIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MyNoConnection(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Non riesco a connettermi ad Internet.", style = MaterialTheme.typography.h5)

        OutlinedButton(
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            border = BorderStroke(3.dp, Color.DarkGray),
            modifier = Modifier.defaultMinSize(80.dp),
            onClick = {

                val scope = CoroutineScope(Dispatchers.Main)

                scope.launch {
                    MyIO.NetworkIO.loadData()
                    navController.navigate("myEntity/l")
                }
            }) {
            Text(text = "Riprova")
        }
    }
}
