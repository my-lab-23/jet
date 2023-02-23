package it.emanueleweb.solaria.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import it.emanueleweb.solaria.MyData.search
import it.emanueleweb.solaria.MyData.searchFav
import it.emanueleweb.solaria.MyData.searchKey

@Composable
fun MySearch(
    search: MutableState<Int>,
    favourite: MutableState<Int>,
    navController: NavHostController
) {

    var text by rememberSaveable { mutableStateOf("Cerca") }
    var color by remember { mutableStateOf(Color.Gray) }

    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = text,
            onValueChange = {

                text = it
                searchKey = it
                if(favourite.value>0) { searchFav(it) } else { search(it) }

                when(search.value) {
                    0 -> search.value = 1
                    1 -> search.value = 2
                    2 -> search.value = 1
                }
            },
            textStyle = TextStyle.Default.copy(fontSize = 15.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = color,
                cursorColor = Color.DarkGray,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.LightGray
            ),
            modifier = Modifier
                .weight(3f)
                .onFocusChanged {
                    if (it.isFocused) {
                        text = ""; color = Color.Black
                    } else {
                        text = "Cerca"; color = Color.Gray
                    }
                }
        )

        MyMenu(favourite, navController)
    }
}
