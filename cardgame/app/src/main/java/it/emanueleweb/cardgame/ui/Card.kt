package it.emanueleweb.cardgame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import it.emanueleweb.cardgame.*

@Composable
fun Card(switch: MutableState<Int>, card: Int, input: Int = R.drawable.vuoto) {

    Image(
        painter = painterResource(id = card),
        contentDescription = "testCard",
        contentScale = ContentScale.Fit,
        modifier = Modifier.clickable {

            if(switch.value==0) {

                selCard = input
                selGiocatoreCardUI = input

                switch.value = 1

            } else {

                selCard = input
                selGiocatoreCardUI = input

                switch.value = 0

            }

            if(input==-42) {

                switch.value = 2
                pause = false
            }
        }
    )
}
