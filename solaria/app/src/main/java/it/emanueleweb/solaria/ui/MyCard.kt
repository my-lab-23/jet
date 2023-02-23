package it.emanueleweb.solaria.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.emanueleweb.solaria.MyData.attrattive
import it.emanueleweb.solaria.MyData.eventi
import it.emanueleweb.solaria.MyData.luoghi
import it.emanueleweb.solaria.db.AppDatabase
import it.emanueleweb.solaria.db.FavDB

@Composable
fun MyCard(id: String, name: String, description: String, navController: NavHostController) {

    val dark = isSystemInDarkTheme()
    val border = if (dark) Color.LightGray else Color.DarkGray
    val star = if (dark) Color.White else Color.Black

    val short = firstFiveWords(description) + "..."

    val context = LocalContext.current
    val favDao = AppDatabase.getDatabase(context).favDao()

    Card(
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(3.dp, Color.DarkGray),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = name, style = MaterialTheme.typography.h5)
            Text(text = short, style = MaterialTheme.typography.body2)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                var color by remember { mutableStateOf(star) }

                if (getFavouriteById(id)) {
                    color = Color.Red
                }

                OutlinedButton(
                    border = BorderStroke(0.dp, Color.Transparent),
                    modifier = Modifier.defaultMinSize(80.dp),
                    onClick = {
                        color = if (color == star) {
                            favDao.insert(FavDB(id))
                            toggleFavourite(id)
                            Color.Red
                        } else {
                            favDao.delete(id)
                            toggleFavourite(id)
                            star
                        }
                    }) {

                    Icon(
                        imageVector = Icons.Default.Star,
                        modifier = Modifier.size(30.dp),
                        contentDescription = "Localized description",
                        tint = color
                    )
                }

                OutlinedButton(
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    border = BorderStroke(3.dp, border),
                    modifier = Modifier.defaultMinSize(80.dp).semantics { contentDescription = id },
                    onClick = {
                        navController.navigate("myInfo/$id")
                    }) {
                    Text(text = "Info")
                }
            }
        }
    }
}

private fun firstFiveWords(sentence: String): String {
    val words = sentence.split(" ")
    val firstFive = words.take(5)
    return firstFive.joinToString(" ")
}

private fun toggleFavourite(id: String) {

    for (luogo in luoghi) {
        if (luogo.id == id) {
            luogo.favourite = !luogo.favourite
            break
        }
    }

    for (attrattiva in attrattive) {
        if (attrattiva.id == id) {
            attrattiva.favourite = !attrattiva.favourite
            break
        }
    }

    for (evento in eventi) {
        if (evento.id == id) {
            evento.favourite = !evento.favourite
            break
        }
    }
}

private fun getFavouriteById(id: String): Boolean {

    for (luogo in luoghi) {
        if (luogo.id == id) {
            return luogo.favourite
        }
    }

    for (attrattiva in attrattive) {
        if (attrattiva.id == id) {
            return attrattiva.favourite
        }
    }

    for (evento in eventi) {
        if (evento.id == id) {
            return evento.favourite
        }
    }

    return false
}
