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
import it.emanueleweb.silver.getFlagResourceForCurrency
import it.emanueleweb.silver.getSilverPrice

@Composable
fun MySilverCard() {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = currency.last,
            fontSize = 36.sp
        )
        SilverPrice(currency =  "CNY")
        SilverPrice(currency =  "EUR")
        SilverPrice(currency =  "RUS")
        SilverPrice(currency =  "USD")
    }
}

@Composable
fun SilverPrice(currency: String) {

    val silverPrice = getSilverPrice(currency)
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
                text = "${"%.2f".format(silverPrice)} $currency/oz",
                fontSize = 36.sp
            )
        }
    }
}
