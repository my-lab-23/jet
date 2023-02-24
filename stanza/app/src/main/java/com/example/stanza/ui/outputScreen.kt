package com.example.stanza.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

import com.example.stanza.data.Contact
import com.example.stanza.data.contacts
import com.example.stanza.data.contactToEdit
import com.example.stanza.db.AppDatabase

@Composable
fun OutputScreen(switch: NavHostController) {

    val context = LocalContext.current
    val contactDao = AppDatabase.getDatabase(context).contactDao()

    // Ordina contacts per cognome
    contacts.sortBy { it.cognome }

    val refresh = rememberSaveable { mutableStateOf(false) }

    // Column with padding
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())
    ) {

        contacts.forEach {

            contact ->

            Row {

                refresh.value = false

                Column {

                    if (!refresh.value) {

                        Button(
                            onClick = {

                                contactDao.delete(
                                    contact.nome,
                                    contact.cognome,
                                    contact.telefono)
                                contacts.remove(contact)
                                refresh.value = true

                            },
                            modifier = Modifier.defaultMinSize(300.dp)
                        ) {
                            Text("Cancella contatto", fontSize = 20.sp)
                        }
                    }

                    //

                    if (!refresh.value) {

                        Button(
                            onClick = {

                                contactToEdit = contact
                                switch.navigate("editScreen")

                            },
                            modifier = Modifier.defaultMinSize(300.dp)
                        ) {
                            Text("Modifica contatto", fontSize = 20.sp)
                        }
                    }
                }

                ContactCard(contact)
            }

            //

        }

        MyButton(switch, 0, "Home")
    }
}

@Composable
fun ContactCard(contact: Contact) {

    Column(Modifier.padding(16.dp)) {

        SelectionContainer { Text("nome: ${contact.nome}", fontSize = 20.sp) }
        SelectionContainer { Text("cognome: ${contact.cognome}", fontSize = 20.sp) }
        SelectionContainer { Text("telefono: ${contact.telefono}", fontSize = 20.sp) }
    }
}
