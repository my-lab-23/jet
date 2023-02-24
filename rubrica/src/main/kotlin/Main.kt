import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.exposed.sql.transactions.transaction

import ui.inputScreen
import ui.editScreen
import ui.outputScreen
import ui.startScreen

import db.connectDB
import db.loadInContacts

fun main() = application {

    connectDB()
    transaction { /*resetTable()*/ }

    loadInContacts()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Rubrica"
    ) {
        myApp()
    }
}

@Composable
fun myApp() {

    val switch = remember { mutableStateOf(0) }

    when (switch.value) {
        0 -> startScreen(switch)
        1 -> inputScreen(switch)
        2 -> outputScreen(switch)
        3 -> editScreen(switch)
    }
}
