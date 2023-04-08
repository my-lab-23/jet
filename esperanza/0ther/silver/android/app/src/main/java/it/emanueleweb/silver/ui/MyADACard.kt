package it.emanueleweb.silver.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.emanueleweb.silver.currency
import it.emanueleweb.silver.getADAPrice
import it.emanueleweb.silver.getFlagResourceForCurrency

@Composable
fun MyADACard() {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = currency.lastADA,
            fontSize = 36.sp
        )
        ADAPrice(currency =  "EUR")
    }
}

@Composable
fun ADAPrice(currency: String) {

    val adaPrice = getADAPrice(currency)
    val flagResource = getFlagResourceForCurrency(currency)

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(flagResource),
            contentDescription = "Flag of $currency",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${"%.2f".format(adaPrice)} $currency",
                fontSize = 36.sp
            )
        }
    }
}
