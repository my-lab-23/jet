package com.example.jalaspot.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jalaspot.executeButtonCommand
import com.example.jalaspot.load
import com.example.jalaspot.save
import kotlinx.coroutines.*

@Composable
fun TopBar() {
    TopAppBar(title = {
        Text(
            text = "Jalaspot",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    })
}

@Composable
fun Img(i: ImageVector = Icons.Default.List, s: String, navController: NavHostController? = null) {

    OutlinedButton(
        onClick = { executeButtonCommand(s, navController) },
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        border = BorderStroke(3.dp, Color.DarkGray),
    ) {
        Icon(
            imageVector = i,
            modifier = Modifier.size(100.dp),
            contentDescription = s,
            tint = Color.DarkGray
        )
    }
}

@Composable
fun DrawButtons(
    ab: List<Int>,
    stringList: List<String>,
    intList: List<ImageVector>,
    navController: NavHostController? = null,
) {

    var i = 0

    repeat(ab[0]) {
        Row(modifier = Modifier.padding(20.dp)) {
            repeat(ab[1]) {
                Box(Modifier.weight(1f)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Img(intList[i], stringList[i], navController)
                        Text(stringList[i], fontSize = 20.sp, color = Color.White)
                    }
                }
                i += 1
            }
        }
    }
}

@Composable
fun MyScrivi(s: String) {

    var text by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
        )

        val coroutineScope = rememberCoroutineScope()

        Button(onClick = {
            val msg = text
            if(s=="load") { coroutineScope.launch { load(msg) } }
            if(s=="save") { coroutineScope.launch { save(msg) } }
            text = ""
        }) {
            Text("INVIA", fontSize = 40.sp)
        }
    }
}
