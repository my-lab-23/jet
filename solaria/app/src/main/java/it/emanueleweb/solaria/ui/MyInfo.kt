package it.emanueleweb.solaria.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import it.emanueleweb.solaria.MyApplication
import it.emanueleweb.solaria.MyData
import it.emanueleweb.solaria.MyIO

@Composable
fun MyInfo(id: String) {

    val orientation = MyApplication.appContext.resources.configuration.orientation

    val idPartOne = id[0]
    val idPartTwo = id[1].code - 48

    when(idPartOne) {

        'l' -> {

            val name = MyData.luoghi[idPartTwo].name
            val description = MyData.luoghi[idPartTwo].description
            val image = MyData.luoghi[idPartTwo].image

            if(orientation==Configuration.ORIENTATION_LANDSCAPE) {
                MyRow(name, description, image)
            } else {
                MyColumn(name, description, image)
            }
        }

        'a' -> {

            val name = MyData.attrattive[idPartTwo].name
            val description = MyData.attrattive[idPartTwo].description
            val image = MyData.attrattive[idPartTwo].image

            if(orientation==Configuration.ORIENTATION_LANDSCAPE) {
                MyRow(name, description, image)
            } else {
                MyColumn(name, description, image)
            }
        }

        'e' -> {

            val name = MyData.eventi[idPartTwo].name
            val description = MyData.eventi[idPartTwo].description
            val image = MyData.eventi[idPartTwo].image

            if(orientation==Configuration.ORIENTATION_LANDSCAPE) {
                MyRow(name, description, image)
            } else {
                MyColumn(name, description, image)
            }
        }
    }
}

@Composable
fun MyColumn(name: String, description: String, image: String) {

        Column(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            Text(text = name, fontSize = 40.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            MyImage(image)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = description, fontSize = 20.sp)
        }
}

@Composable
fun MyRow(name: String, description: String, image: String) {

    Row(
        modifier = Modifier.padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            MyImage(image)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = name, fontSize = 40.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = description, fontSize = 20.sp)
        }
    }
}

@Composable
fun MyImage(image: String) {

    val url = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = "url") {
        url.value = MyIO.NetworkIO.getFirebaseUrl(image)
    }

    if(url.value!="") {

        SubcomposeAsyncImage(
            model = url.value,
            contentDescription = null,
            loading = { CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                color = Color.Gray
            ) },
        )
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.Gray
        )
    }
}
