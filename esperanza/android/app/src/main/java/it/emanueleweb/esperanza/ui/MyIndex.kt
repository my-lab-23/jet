package it.emanueleweb.esperanza.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun AnonymousEncouragementMessageScreen(onSendMessage: (message: String) -> Unit) {

    var message by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.padding(50.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Inviare un messaggio d'incoraggiamento anonimo",
            style = MaterialTheme.typography.h4,
            color = Color(0xFF5D7F5B)
        )

        Text(
            text = "Inserisci il messaggio:",
            style = MaterialTheme.typography.h6,
            color = Color(0xFF5D7F5B)
        )

        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            label = { Text("Messaggio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 8,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color(0xFF5D7F5B),
                focusedIndicatorColor = Color(0xFF5D7F5B),
                focusedLabelColor = Color(0xFF5D7F5B),
                backgroundColor = Color.White
            )
        )

        Button(
            onClick = {
                onSendMessage(message.text)
                message = TextFieldValue("")
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5D7F5B))
        ) {
            Text(
                text = "Invia il messaggio",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }

    //

    val sharedPref = LocalContext.current.getSharedPreferences("myPref", Context.MODE_PRIVATE)

    LaunchedEffect(Unit) {

        val savedMessage = sharedPref.getString("message", "")

        if (savedMessage != null && savedMessage.isNotEmpty()) {
            
            message = TextFieldValue(savedMessage)
        }

        while (true) {
            
            withContext(Dispatchers.IO) {
            
                sharedPref.edit().putString("message", message.text).apply()
            }
            
            delay(1000)
        }
    }
}
