import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.FileReader

@Composable
fun myLeggi() {

    val msgs = remember { mutableStateOf(msgs()) }

    var c = 0

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())
    ) {
            for (msg in msgs.value) {

            if (c == 0 && msg != "") {

                println(msg)

                SelectionContainer {
                    Text(
                        msg, fontSize = 25.sp,
                        modifier = Modifier.background(Color.LightGray)
                            .fillMaxWidth()
                    )
                }

                c = 1
            } else if ((c == 1 && msg != "")) {

                SelectionContainer {
                    Text(
                        msg, fontSize = 25.sp,
                        modifier = Modifier.background(Color.Gray)
                            .fillMaxWidth()
                    )
                }

                c = 0
            }
        }

        Button(onClick = {
            msgs.value = msgs()
        }) {
            Text("CONTROLLA", fontSize = 40.sp)
        }
    }
}

fun msgs(): List<String> {

    return FileReader("/home/ema/data/data").readLines().toString()
        .replace("[", "").replace("]", "").replace(" , ", " ")
        .split("|*|").reversed()
}
