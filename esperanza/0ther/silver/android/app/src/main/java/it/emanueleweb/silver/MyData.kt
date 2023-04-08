package it.emanueleweb.silver

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

val currency = Currency()
private val client = HttpClient(CIO)

data class Currency(
    var cny: Double = 0.0,
    var eur: Double = 0.0,
    var eurADA: Double = 0.0,
    var rus: Double = 0.0,
    var usd: Double = 0.0,
    var last: String = "Last updated: -",
    var lastADA: String = "Last updated: -"
)

@Serializable
data class ExchangeRate(
    val success: Boolean,
    val base: String,
    val timestamp: Int,
    val rates: Map<String, Float>
)

@Serializable
data class ADAExchangeRate(
    val price: Double,
    val timestamp: String
)

//

fun getSilverPrice(c: String): Double {

    when(c) {

        "CNY" -> return currency.cny
        "EUR" -> return currency.eur
        "RUS" -> return currency.rus
        "USD" -> return currency.usd
    }

    return -1.0
}

fun getADAPrice(c: String): Double {

    when(c) {

        "EUR" -> return currency.eurADA
    }

    return -1.0
}

fun getFlagResourceForCurrency(currency: String): Int {

    when(currency) {

        "CNY" -> return R.drawable.cn
        "EUR" -> return R.drawable.eu
        "RUS" -> return R.drawable.ru
        "USD" -> return R.drawable.us
    }

    return -1
}

suspend fun getData(accessToken: String) {

    try {

        val url = "https://2desperados.it/silver/android/$accessToken/silver"
        val data = client.request(url).bodyAsText()

        populateCurrency(data)

    } catch (e: Exception) {

        e.printStackTrace()
        println("Errore!")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun getDataADA(accessToken: String) {

    try {

        val url = "https://2desperados.it/silver/android/$accessToken/ada"
        val data = client.request(url).bodyAsText()

        populateCurrencyADA(data)

    } catch (e: Exception) {

        e.printStackTrace()
        println("Errore!")
    }
}

fun populateCurrency(data: String) {

    val exchangeRate = Json.decodeFromString<ExchangeRate>(data)

    currency.cny = exchangeRate.rates["CNY"]!!.toDouble()
    currency.eur = exchangeRate.rates["EUR"]!!.toDouble()
    currency.rus = exchangeRate.rates["RUB"]!!.toDouble()
    currency.usd = exchangeRate.rates["USD"]!!.toDouble()
    val last = formatTimestamp(exchangeRate.timestamp.toLong() * 1000)
    currency.last = "Last updated: $last"
}

@RequiresApi(Build.VERSION_CODES.O)
fun populateCurrencyADA(data: String) {

    val exchangeRate = Json.decodeFromString<ADAExchangeRate>(data)

    currency.eurADA = exchangeRate.price
    val last = formatTimestamp(Instant.parse(exchangeRate.timestamp).toEpochMilli())
    currency.lastADA = "Last updated: $last"
}

@SuppressLint("SimpleDateFormat")
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy, HH:mm")
    val date = Date(timestamp)
    return sdf.format(date)
}
