package com.example.scrivi.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scrivi.AppDatabase
import com.example.scrivi.MsgDB
import com.example.scrivi.MyMessage
import com.example.scrivi.invia
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyScrivi() {

    val isConnected = rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }

    var color = Color.Red
    if(isConnected.value) color = Color.Green

    val context = LocalContext.current
    val msgDao = AppDatabase.getDatabase(context).msgDao()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        MyBox1(color)

        //

        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
        )

        //

        MyButton1(isConnected)

        //

        val coroutineScope = rememberCoroutineScope()

        Button(onClick = {
            val msg = MyMessage(text)

            if(isConnected.value) {
                coroutineScope.launch {

                    msgDao.loadAll().forEach {
                        val msgD = MyMessage(it.msg)
                        invia(msgD.getJsonObject())
                    }

                    msgDao.deleteAll()

                    invia(msg.getJsonObject())
                }
            } else {
                msgDao.insert(MsgDB(msg.msg))
            }

            text = ""
        }) {
            Text("INVIA", fontSize = 40.sp)
        }
    }
}

@Composable
fun MyBox1(color: Color) {

    Box(
        Modifier
            .background(color)
            .fillMaxWidth()) {
        Text("", fontSize = 40.sp)
    }
}

@Composable
fun MyButton1(isConnected: MutableState<Boolean>) {

    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            isConnected.value = com.example.scrivi.isConnected("http://192.168.1.23:8080/")
        }
    }) {
        Text("CONNESSIONE", fontSize = 40.sp)
    }
}
