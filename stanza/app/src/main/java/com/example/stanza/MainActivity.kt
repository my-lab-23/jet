package com.example.stanza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stanza.db.AppDatabase
import com.example.stanza.ui.EditScreen
import com.example.stanza.ui.InputScreen
import com.example.stanza.ui.OutputScreen
import com.example.stanza.ui.StartScreen
import com.example.stanza.data.fromContactDBToContact
import kotlin.concurrent.thread

import com.example.stanza.data.contacts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext
        val contactDao = AppDatabase.getDatabase(context).contactDao()

        if(contacts.isEmpty()) {

            thread {
                contactDao.loadAll().forEach {
                    contacts.add(fromContactDBToContact(it))
                }
            }
        }

        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "startScreen") {
                composable("startScreen") { StartScreen(navController) }
                composable("inputScreen") { InputScreen(navController)  }
                composable("outputScreen") { OutputScreen(navController) }
                composable("editScreen") { EditScreen(navController) }
            }
        }
    }
}
