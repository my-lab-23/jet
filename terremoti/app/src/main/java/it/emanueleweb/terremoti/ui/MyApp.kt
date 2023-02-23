package it.emanueleweb.terremoti.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.emanueleweb.terremoti.MyData
import it.emanueleweb.terremoti.myGeoJson

@Composable
fun MyApp() {

    Column(modifier = Modifier.padding(16.dp)) {

        val switch = rememberSaveable { mutableStateOf(0) }
        val i = rememberSaveable { mutableStateOf(0) }
        val myData = myGeoJson()

        myData.forEach { println(it.title) }

        Row(modifier = Modifier.weight(7f)) {

            when (switch.value) {

                0 -> MyMap(myData[i.value].latitude, myData[i.value].longitude)
                1 -> MyMap(myData[i.value].latitude, myData[i.value].longitude)
                else -> MyMap(myData[0].latitude, myData[0].longitude)
            }
        }

        Row(modifier = Modifier.weight(1f)) {

            Text("${myData[i.value].date}: ${myData[i.value].title}", fontSize = 20.sp)
        }

        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = {

                i.value = 0
                if(switch.value == 0) { switch.value = 1 } else { switch.value = 0 }

            }, modifier = Modifier.defaultMinSize(80.dp)) {

                Text("0", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {

                if(i.value>0) { i.value-- }
                if(switch.value == 0) { switch.value = 1 } else { switch.value = 0 }

            }, modifier = Modifier.defaultMinSize(80.dp)) {

                Text("-", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {

                if(i.value<myData.size-1) { i.value++ }
                if(switch.value == 0) { switch.value = 1 } else { switch.value = 0 }

            }, modifier = Modifier.defaultMinSize(80.dp)) {

                Text("+", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {

                i.value = myData.size-1
                if(switch.value == 0) { switch.value = 1 } else { switch.value = 0 }

            }, modifier = Modifier.defaultMinSize(80.dp)) {

                Text("L", fontSize = 32.sp)
            }
        }
    }
}
