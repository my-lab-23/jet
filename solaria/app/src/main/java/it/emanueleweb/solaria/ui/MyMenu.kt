package it.emanueleweb.solaria.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import it.emanueleweb.solaria.MyData.searchFav
import it.emanueleweb.solaria.MyData.searchKey

@Composable
fun MyMenu(
    favourite: MutableState<Int>,
    navController: NavHostController
) {

    val mDisplayMenu = remember { mutableStateOf(false) }

    IconButton(onClick = { mDisplayMenu.value = !mDisplayMenu.value }) {
        Icon(Icons.Default.MoreVert, "")
    }

    Box {
        DropdownMenu(
            expanded = mDisplayMenu.value,
            onDismissRequest = { mDisplayMenu.value = false }
        ) {
            DropdownMenuItem(onClick = {
                when(favourite.value) {
                    0 -> favourite.value = 1
                    1 -> favourite.value = 2
                    2 -> favourite.value = 1
                }
                searchFav(searchKey)
                mDisplayMenu.value = false
            }) {
                Text(text = "Favoriti")
            }

            DropdownMenuItem(onClick = {
                navController.navigate("myAbout")
                mDisplayMenu.value = false
            }) {
                Text(text = "About")
            }
        }
    }
}
