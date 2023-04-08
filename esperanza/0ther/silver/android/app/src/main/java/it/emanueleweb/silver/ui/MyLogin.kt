package it.emanueleweb.silver.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import it.emanueleweb.silver.getData
import it.emanueleweb.silver.getDataADA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Login(
    navController: NavController,
    manager: CredentialsManager,
    loginWithBrowser: (CredentialsManager) -> Unit,
    showToastMessage: (String) -> Unit
) {

    val scope = CoroutineScope(Dispatchers.Main)

    Button(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(16.dp),

        onClick = {
            val loggedIn = manager.hasValidCredentials()
            if(!loggedIn) {
                loginWithBrowser(manager)
            } else {
                manager.getCredentials(object: Callback<Credentials, CredentialsManagerException> {
                    override fun onSuccess(result: Credentials) {
                        Thread { showToastMessage("Success!") }.start()
                        val l = scope.launch {
                            getData(result.accessToken)
                            getDataADA(result.accessToken)
                        }
                        while(!l.isCompleted) { /* Wait */ }
                        scope.launch { navController.navigate("mytab") }
                    }
                    override fun onFailure(error: CredentialsManagerException) {
                        Thread { showToastMessage("Failure!") }.start()
                    }
                })
            }
        },
    ) {
        Text("Login")
    }
}
