import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.IOException

val client = HttpClient(CIO)

suspend fun url(): String {

    return try {
        val address = "https://opentdb.com/api.php?amount=50" +
                "&category=${MySetting.category}" +
                "&difficulty=${MySetting.difficultyLevel.lowercase()}" +
                "&type=multiple"
        val response: HttpResponse = client.get(address)

        println(address)
        println(response.bodyAsText())

        response.bodyAsText()
    } catch (e: IOException) {
        "Error!"
    }
}
