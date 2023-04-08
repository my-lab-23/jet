package it.emanueleweb.silver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import it.emanueleweb.silver.ui.theme.SilverTheme
import android.os.Handler
import android.os.Looper
import android.widget.Toast
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
import com.auth0.android.callback.Callback
import it.emanueleweb.silver.ui.Login
import it.emanueleweb.silver.ui.MySilverCard
import it.emanueleweb.silver.ui.SwitchableTabs

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
            SilverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    Modifier.fillMaxSize(), color = Color(0xFFC0C0C0)
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

                        composable("mytab") { SwitchableTabs() }
                    }
                }
            }
        }
    }
}
