package ui

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
import org.jetbrains.exposed.sql.transactions.transaction
import androidx.compose.foundation.text.selection.SelectionContainer

import data.Contact
import data.contacts
import data.contactToEdit

import db.deleteRow

@Composable
fun outputScreen(switch: MutableState<Int>) {

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

                                transaction { deleteRow(contact) }
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
                                switch.value = 3

                            },
                            modifier = Modifier.defaultMinSize(300.dp)
                        ) {
                            Text("Modifica contatto", fontSize = 20.sp)
                        }
                    }
                }

                contactCard(contact)
            }

            //

        }

        myButton(switch, 0, "Home")
    }
}

@Composable
fun contactCard(contact: Contact) {

    Column(Modifier.padding(16.dp)) {

        SelectionContainer { Text("nome: ${contact.nome}", fontSize = 20.sp) }
        SelectionContainer { Text("cognome: ${contact.cognome}", fontSize = 20.sp) }
        SelectionContainer { Text("telefono: ${contact.telefono}", fontSize = 20.sp) }
    }
}
