package com.example.novena2

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.novena2.Aux.convertToFalseArrayIfAllTrue
import com.example.novena2.Aux.highlightSquare
import com.example.novena2.Synchro.update
import com.example.novena2.Synchro.syncroBackup
import com.example.novena2.ui.theme.Novena2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URI

//

const val apiKey = ""
const val adr = "wss://2desperados.it/novena/"

val clientWebSocket = WebSocketClientExample(URI("${adr}ws"), apiKey)

var greens = BooleanArray(9) { false }
var isUpdate = -1

//

class MainActivity : ComponentActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clientWebSocket.connect()
        update()

        greens = convertToFalseArrayIfAllTrue(greens)

        setContent {
            Novena2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Grid()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveBooleanArrayToSharedPreferences(this, greens, "my_boolean_array")

        //

        greens = loadBooleanArrayFromSharedPreferences(
            this,
            "my_boolean_array",
            BooleanArray(9) { false }
        )

        Log.d("MySizeOnStop", "${greens.size}")

        //

        val c = scope.launch {
            syncroBackup()
            clientWebSocket.sendMsg("disconnect")
        }
        while(!c.isCompleted) { /* --- */ }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Grid() {

    var isUpdateCompo by remember { mutableStateOf(isUpdate) }

    LaunchedEffect(Unit) {

        while(true) {
            delay(500)
            isUpdateCompo = isUpdate
            Log.d("Launched", greens.joinToString())
        }
    }

    if(isUpdateCompo==-1) { GridExample() } else { GridExample() }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GridExample() {

    val gridSize = 3
    var count = 0

    Log.d("ComposableGridExample", greens.joinToString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        repeat(gridSize) {
            Row {
                repeat(gridSize) {
                    Rectangle(
                        modifier = Modifier
                            .weight(1f)
                            .defaultMinSize(100.dp, 150.dp)
                            .padding(4.dp),
                        color = Color.Yellow,
                        count
                    )
                    if(count<8) count++
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Rectangle(
    modifier: Modifier = Modifier,
    color: Color,
    count: Int,
) {
    var rectangleColor by remember { mutableStateOf(color) }
    var rectangleId by remember { mutableStateOf("") }

    val red = highlightSquare()
    if (greens[count]) rectangleColor = Color.Green
    else if (red == count) rectangleColor = Color.Red

    Log.d("ComposableRectangle", greens.joinToString())

    Box(
        modifier = modifier
            .clickable {
                rectangleColor = Color.Green
                rectangleId = "$count"
                greens[count] = true

                val scope = CoroutineScope(Dispatchers.IO)
                scope.launch { syncroBackup() }
            }
            .background(rectangleColor)
    )
}

fun saveBooleanArrayToSharedPreferences(context: Context, array: BooleanArray, key: String) {
    val sharedPrefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPrefs.edit()
    val booleanList = array.map { it.toString() }
    val booleanString = booleanList.joinToString(",")
    editor.putString(key, booleanString)
    editor.apply()
}

fun loadBooleanArrayFromSharedPreferences(context: Context, key: String, defaultArray: BooleanArray): BooleanArray {
    val sharedPrefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val booleanString = sharedPrefs.getString(key, null)
    return if (booleanString != null) {
        val booleanList = booleanString.split(",").map { it.toBoolean() }
        booleanList.toBooleanArray()
    } else {
        defaultArray
    }
}
