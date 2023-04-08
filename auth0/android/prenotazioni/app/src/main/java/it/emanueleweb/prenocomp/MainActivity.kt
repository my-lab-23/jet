package it.emanueleweb.prenocomp

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import it.emanueleweb.prenocomp.ui.Login
import it.emanueleweb.prenocomp.ui.PrenotazioneScreen
import it.emanueleweb.prenocomp.ui.theme.PrenocompTheme
import com.auth0.android.callback.Callback
import it.emanueleweb.prenocomp.ui.Grazie

class MainActivity : ComponentActivity() {

    private lateinit var account: Auth0

    private fun loginWithBrowser(manager: CredentialsManager) {
        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid email offline_access")
            .start(this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Thread { showToastMessage("Failure!") }.start()
                }
                override fun onSuccess(result: Credentials) {
                    manager.saveCredentials(result)
                    Thread { showToastMessage("Success!") }.start()
                }
            })
    }

    fun showToastMessage(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )

        val auth0 = Auth0(this)
        val apiClient = AuthenticationAPIClient(auth0)
        val manager = CredentialsManager(apiClient, SharedPreferencesStorage(this))

        setContent {
            PrenocompTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {

                        composable("login") {

                            Login(
                                navController = navController,
                                manager = manager,
                                loginWithBrowser = { loginWithBrowser(it) },
                                showToastMessage = { showToastMessage(it) }
                            )
                        }

                        composable("prenotazione") {

                            PrenotazioneScreen(
                                navController = navController,
                                manager = manager,
                                showToastMessage = { showToastMessage(it) }
                            )
                        }

                        composable("grazie") { Grazie(navController) }
                    }
                }
            }
        }
    }
}

data class Prenotazione(
    val nome: String,
    val cognome: String,
    val email: String,
    val telefono: String,
    val servizio: String,
    val data: String,
    val ora: String
)

suspend fun prenota(prenotazione: Prenotazione, accessToken: String) {
    send(prenotazione, accessToken)
}

private val client = HttpClient(CIO)

private suspend fun send(prenotazione: Prenotazione, accessToken: String) {

    try {

        client.post("https://2desperados.it/other/prenotazioni/android") {
            setBody(prenotazione.toString())
            headers {
                append("Authorization", "Bearer $accessToken")
            }
        }

        println ("Prenotazione effettuata!")

    } catch (e: Exception) {

        e.printStackTrace()
        println("Errore!")
    }
}
