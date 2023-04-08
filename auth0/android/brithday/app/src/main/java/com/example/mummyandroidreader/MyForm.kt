package com.example.mummyandroidreader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.example.mummyandroidreader.MyHTTP.sendToken
import kotlinx.coroutines.launch

@Composable
fun MyForm(manager: CredentialsManager) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(response, modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = {
                scope.launch {

                    //

                    manager.getCredentials(object : Callback<Credentials, CredentialsManagerException> {
                        override fun onSuccess(result: Credentials) {
                            scope.launch {
                                response = sendToken(firstName, lastName, result.accessToken)
                            }
                        }
                        override fun onFailure(error: CredentialsManagerException) {
                            scope.launch {
                                response = error.message ?: "Error"
                            }
                        }
                    })

                    //
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}
