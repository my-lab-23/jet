package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.exposed.sql.transactions.transaction

import data.Contact
import data.contacts
import data.contactToEdit

import db.createRow
import db.deleteRow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun editScreen(switch: MutableState<Int>) {

    val focusManager = LocalFocusManager.current
    val (uno, due, tre) = FocusRequester.createRefs()

    var input0 by rememberSaveable { mutableStateOf(contactToEdit.nome) }
    var input1 by rememberSaveable { mutableStateOf(contactToEdit.cognome) }
    var input2 by rememberSaveable { mutableStateOf(contactToEdit.telefono) }

    // Column with padding
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier.padding(16.dp)
            .fillMaxSize()
            .onKeyEvent {

                when(it.key) {

                    Key.DirectionDown -> {
                        focusManager.moveFocus(FocusDirection.Down)
                    }

                    Key.DirectionUp -> {
                        focusManager.moveFocus(FocusDirection.Previous)
                    }

                    else -> false
                }
            }
    ) {

        //

        var color0 by remember { mutableStateOf(Color.Gray) }

        TextField(
            value = input0,
            onValueChange = { input0 = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp, color = color0),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .focusOrder(uno) { down = due; previous = tre }
                .onFocusChanged {
                    if (it.isFocused) {
                        input0 = ""
                        color0 = Color.Black
                    }
                }
        )

        //

        var color1 by remember { mutableStateOf(Color.Gray) }

        TextField(
            value = input1,
            onValueChange = { input1 = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp, color = color1),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .focusOrder(due) { down = tre; previous = uno }
                .onFocusChanged {
                    if (it.isFocused) {
                        input1 = ""
                        color1 = Color.Black
                    }
                }
        )

        //

        var color2 by remember { mutableStateOf(Color.Gray) }

        TextField(
            value = input2,
            onValueChange = { input2 = it },
            textStyle = TextStyle.Default.copy(fontSize = 40.sp, color = color2),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .focusOrder(tre) { down = uno; previous = due }
                .onFocusChanged {
                    if (it.isFocused) {
                        input2 = ""
                        color2 = Color.Black
                    }
                }
        )

        //

        Button(
            onClick = {

                // Remove old contact

                contacts.remove(contactToEdit)
                transaction { deleteRow(contactToEdit) }

                // Check if contact already exists

                var exists = false

                contacts.forEach {
                    if(it.nome == input0 && it.cognome == input1 && it.telefono == input2) {
                        exists = true
                    }
                }

                //

                if(!exists) {
                    transaction { createRow(input0, input1, input2) }
                    contacts.add(Contact(input0, input1, input2))
                }

                //

                input0 = "nome"; input1 = "cognome"; input2 = "telefono"
                color0 = Color.Gray; color1 = Color.Gray; color2 = Color.Gray

                //

                switch.value = 2

            },
            modifier = Modifier.defaultMinSize(400.dp)
        ) {
            Text("Modifica contatto", fontSize = 40.sp)
        }

        //

        myButton(switch, 0, "Home")
    }
}
