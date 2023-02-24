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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.emanueleweb.cardgame.*
import it.emanueleweb.cardgame.R
import it.emanueleweb.cardgame.logic.wins1
import it.emanueleweb.cardgame.logic.wins2

@Composable
fun GameBoard() {

    val switch = rememberSaveable { mutableStateOf(0) }

    when (switch.value) {

        0 -> { Selector(switch) }

        1 -> { Selector(switch) }

        2 -> { Selector(switch) }
    }
}

@Composable
fun Selector(switch: MutableState<Int>) {

    if(selGiocatoreCardUI in 1..3 && selComputerCardUI in 0..2) {

        Aux2(switch, giocatoreCardsToRes[selGiocatoreCardUI-1], computerCardsToRes[selComputerCardUI])

    } else if(selGiocatoreCardUI in 1..3) {

        Aux(switch, giocatoreCardsToRes[selGiocatoreCardUI-1], R.drawable.vuoto)

    } else if(selComputerCardUI in 0..2) {

        Aux(switch, R.drawable.vuoto, computerCardsToRes[selComputerCardUI])

    } else {

        Aux(switch, R.drawable.vuoto, R.drawable.vuoto)

    }
}

@Composable
fun Aux(switch: MutableState<Int>, img1: Int, img2: Int) {

    Row(modifier = Modifier.padding(16.dp)) {

        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(wins1.toString(), fontSize = 50.sp, color = Color.Black)
        }

        Column(
            modifier = Modifier
                .background(color = Color.Green)
                .weight(1f)
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Card(switch, R.drawable.retro)
                Spacer(modifier = Modifier.width(32.dp))
                Card(switch, R.drawable.retro)
                Spacer(modifier = Modifier.width(32.dp))
                Card(switch, R.drawable.retro)
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                Card(switch, img1, -42)
                Spacer(modifier = Modifier.width(32.dp))
                Card(switch, img2, -42)
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                Card(switch, giocatoreCardsToRes[0], 1)
                Spacer(modifier = Modifier.width(32.dp))
                Card(switch, giocatoreCardsToRes[1], 2)
                Spacer(modifier = Modifier.width(32.dp))
                Card(switch, giocatoreCardsToRes[2], 3)
            }
        }

        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(wins2.toString(), fontSize = 50.sp, color = Color.Black)
        }
    }
}

@Composable
fun Aux2(switch: MutableState<Int>, img1: Int, img2: Int) {

    Row(modifier = Modifier.padding(16.dp)) {

        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(wins1.toString(), fontSize = 50.sp, color = Color.Black)
        }

        Column(
            modifier = Modifier
                .background(color = Color.Green)
                .weight(1f)
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                Card(switch, img1, -42)
                Spacer(modifier = Modifier.width(32.dp))
                Card(switch, img2, -42)
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

            }
        }

        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .weight(0.5f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(wins2.toString(), fontSize = 50.sp, color = Color.Black)
        }
    }
}
