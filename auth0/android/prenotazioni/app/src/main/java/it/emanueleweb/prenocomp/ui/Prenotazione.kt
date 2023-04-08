package it.emanueleweb.prenocomp.ui

import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import it.emanueleweb.prenocomp.Prenotazione
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import com.auth0.android.callback.Callback
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.result.Credentials
import it.emanueleweb.prenocomp.prenota

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrenotazioneScreen(
    navController: NavController,
    manager: CredentialsManager,
    showToastMessage: (String) -> Unit
) {

    val nome = remember { mutableStateOf("") }
    val cognome = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val servizio = remember { mutableStateOf("") }
    val data = remember { mutableStateOf(LocalDate.now().toString()) }
    val ora = remember { mutableStateOf(LocalTime.now().toString()) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Inserisci i tuoi dati per prenotare il servizio:",
            fontSize = 20.sp,
            // Bold text
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nome.value,
            onValueChange = { nome.value = it },
            label = { Text(text = "Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cognome.value,
            onValueChange = { cognome.value = it },
            label = { Text(text = "Cognome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(text = "Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = telefono.value,
            onValueChange = { telefono.value = it },
            label = { Text(text = "Telefono") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Clicca per scegliere il servizio:",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownDemo(servizio)

        Spacer(modifier = Modifier.height(16.dp))

        //

        AndroidView(factory = { context ->
            DatePicker(context).apply {
                // Set the initial date value using the date stored in `data`
                val initialDate = LocalDate.parse(data.value)
                updateDate(initialDate.year, initialDate.monthValue - 1, initialDate.dayOfMonth)

                // Set a listener to update the value of `data` when the date is changed
                setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                    data.value = LocalDate.of(year, monthOfYear + 1, dayOfMonth).toString()
                }
            }
        })

        //

        Spacer(modifier = Modifier.height(16.dp))

        //

        AndroidView(factory = { context ->
            TimePicker(context).apply {
                // Set the initial time value using the date stored in `ora`
                val initialTime = LocalTime.parse(ora.value)
                hour = initialTime.hour
                minute = initialTime.minute
                // Set a listener to update the value of `time` when the date is changed
                setOnTimeChangedListener { _, hourOfDay, minute ->
                    ora.value = LocalTime.of(hourOfDay, minute).toString()
                }
            }
        })

        //

        Spacer(modifier = Modifier.height(16.dp))

        val scope = rememberCoroutineScope()

        Button(
            onClick = {

                val prenotazione = Prenotazione(
                    nome = nome.value,
                    cognome = cognome.value,
                    email = email.value,
                    telefono = telefono.value,
                    servizio = servizio.value,
                    data = data.value,
                    ora = ora.value
                )

                manager.getCredentials(object : Callback<Credentials, CredentialsManagerException> {
                    override fun onSuccess(result: Credentials) {
                        scope.launch { prenota(prenotazione, result.accessToken) }
                        Thread { showToastMessage("Success!") }.start()
                        scope.launch { navController.navigate("grazie") }
                    }
                    override fun onFailure(error: CredentialsManagerException) {
                        Thread { showToastMessage("Failure!") }.start()
                    }
                })

        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Prenota")
        }
    }
}

@Composable
fun DropdownDemo(servizio: MutableState<String>) {

    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Servizio 1", "Servizio 2", "Servizio 3")
    val disabledValue = ""
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopStart)) {
        Text(" " + items[selectedIndex], color = Color.White, fontSize = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                //.height(56.dp)
                .clickable(onClick = { expanded = true }).background(Color(0xFF009788)))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    servizio.value = s
                }) {
                    val disabledText = if (s == disabledValue) { " (Disabled)" } else { "" }
                    Text(text = s + disabledText, color = Color.Black, fontSize = 20.sp)
                }
            }
        }
    }
}
