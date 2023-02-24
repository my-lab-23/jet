package it.emanueleweb.cardgame.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.emanueleweb.cardgame.*
import it.emanueleweb.cardgame.R
import it.emanueleweb.cardgame.logic.wins1
import it.emanueleweb.cardgame.logic.wins2

@Composable
fun VerticalGameBoard() {

    val switch = rememberSaveable { mutableStateOf(0) }

    when (switch.value) {

        0 -> { VerticalSelector(switch) }

        1 -> { VerticalSelector(switch) }

        2 -> { VerticalSelector(switch) }
    }
}

@Composable
fun VerticalSelector(switch: MutableState<Int>) {

    if(selGiocatoreCardUI in 1..3 && selComputerCardUI in 0..2) {

        VerticalAux2(switch, giocatoreCardsToRes[selGiocatoreCardUI-1], computerCardsToRes[selComputerCardUI])

    } else if(selGiocatoreCardUI in 1..3) {

        VerticalAux(switch, giocatoreCardsToRes[selGiocatoreCardUI-1], R.drawable.vuoto)

    } else if(selComputerCardUI in 0..2) {

        VerticalAux(switch, R.drawable.vuoto, computerCardsToRes[selComputerCardUI])

    } else {

        VerticalAux(switch, R.drawable.vuoto, R.drawable.vuoto)

    }
}

@Composable
fun VerticalAux(switch: MutableState<Int>, img1: Int, img2: Int) {

    Column {

        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(wins2.toString(), fontSize = 50.sp, color = Color.Black)
        }

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {

            Column(modifier = Modifier.padding(16.dp).weight(1f)) {

                Card(switch, R.drawable.retro)
            }

            Column(modifier = Modifier.padding(16.dp).weight(1f)) {

                Card(switch, R.drawable.retro)
            }

            Column(modifier = Modifier.padding(16.dp).weight(1f)) {

                Card(switch, R.drawable.retro)
            }
        }

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {

            Card(switch, img1, -42)
            Spacer(modifier = Modifier.width(32.dp))
            Card(switch, img2, -42)
        }

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {

            Column(modifier = Modifier.padding(16.dp).weight(1f)) {

                Card(switch, giocatoreCardsToRes[0], 1)
            }

            Column(modifier = Modifier.padding(16.dp).weight(1f)) {

                Card(switch, giocatoreCardsToRes[1], 2)
            }

            Column(modifier = Modifier.padding(16.dp).weight(1f)) {

                Card(switch, giocatoreCardsToRes[2], 3)
            }
        }

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(wins1.toString(), fontSize = 50.sp, color = Color.Black)
        }
    }
}

@Composable
fun VerticalAux2(switch: MutableState<Int>, img1: Int, img2: Int) {

    Column {

        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(wins2.toString(), fontSize = 50.sp, color = Color.Black)
        }

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {

            Card(switch, img1, -42)
            Spacer(modifier = Modifier.width(32.dp))
            Card(switch, img2, -42)
        }

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .background(color = Color.Green),
            horizontalArrangement = Arrangement.Center
        ) {}

        //

        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(wins1.toString(), fontSize = 50.sp, color = Color.Black)
        }
    }
}
