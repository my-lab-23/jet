package com.example.bibbia

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val versetto = rememberSaveable { mutableStateOf("") }

            Surface(color = MaterialTheme.colors.background) {
                val context = LocalContext.current
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Bibbia Random",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Left,
                                    fontSize = 20.sp
                                )
                            }
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        SelectionContainer {

                            Text(
                                text = versetto.value,
                                modifier = Modifier
                                    .padding(10.dp),
                                fontSize = 40.sp
                            )
                        }

                        Button(
                            onClick = {

                                val scope = CoroutineScope(Dispatchers.IO)
                                scope.launch { versetto.value = verse() }
                                setAlarm(context)
                            }
                        ) {
                            Text(text = "Versetto", fontSize = 40.sp)
                        }
                    }
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(context: Context) {
        val timeSec = System.currentTimeMillis()
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeSec, 86_000_000, pendingIntent)
    }
}
