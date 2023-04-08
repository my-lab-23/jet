package com.example.mummyandroidreader

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class MainActivity : AppCompatActivity() {

    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //

        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )

        val auth0 = Auth0(this)
        val apiClient = AuthenticationAPIClient(auth0)
        val manager = CredentialsManager(apiClient, SharedPreferencesStorage(this))

        //

        fun showToastMessage(message: String) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }

        //

        fun loginWithBrowser() {
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

        //

        val button1 = findViewById<Button>(R.id.button1)

        button1.setOnClickListener {
            val loggedIn = manager.hasValidCredentials()
            if(!loggedIn) {
                loginWithBrowser()
            } else {
                manager.getCredentials(object: Callback<Credentials, CredentialsManagerException> {
                    override fun onSuccess(result: Credentials) {
                        Thread { showToastMessage("Success!") }.start()
                    }
                    override fun onFailure(error: CredentialsManagerException) {
                        Thread { showToastMessage("Failure!") }.start()
                    }
                })
            }
        }

        findViewById<ComposeView>(R.id.composeView1).setContent { MyScreen(manager) }
    }
}

@Composable
fun MyScreen(manager: CredentialsManager) {

    Column {
        Spacer(modifier = Modifier.height(70.dp))
        MyForm(manager)
    }
}
