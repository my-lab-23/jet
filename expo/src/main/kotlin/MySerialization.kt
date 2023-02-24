import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileReader
import java.io.FileWriter

@Serializable
private data class Trivia(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)

suspend fun loadJsonFile() {

    val fileData = url()

    //save(fileData, "trivia.txt", Context.MODE_PRIVATE)
    withContext(Dispatchers.IO) {
        FileWriter("data.txt", true).use { it.write(fileData) }
        FileWriter("data.txt", true).use { it.write("\n") }
    }

    withContext(Dispatchers.IO) {
        Thread.sleep(10_000)
    }
}

fun loadJsonArray(i: Int): JSONArray {

    //val fileData = load("trivia.txt")
    val fileData = FileReader("data.txt").readLines()

    return JSONObject(fileData[i]).getJSONArray("results")
}

fun loadQuestion(i: Int, j: Int): String {

    val jsonArray = loadJsonArray(j)
    val format = Json { ignoreUnknownKeys = true }

    //val fromApi = format.decodeFromString<Trivia>(jsonArray[index].toString()).question
    //return HtmlCompat.fromHtml(fromApi, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    return format.decodeFromString<Trivia>(jsonArray[i].toString()).question
}

fun loadCorrect(i: Int, j: Int): String {

    val jsonArray = loadJsonArray(j)
    val format = Json { ignoreUnknownKeys = true }

    //val fromApi = format.decodeFromString<Trivia>(jsonArray[index].toString()).correct_answer
    //return HtmlCompat.fromHtml(fromApi, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    return format.decodeFromString<Trivia>(jsonArray[i].toString()).correct_answer
}

fun loadIncorrect(i: Int, j: Int): List<String> {

    val jsonArray = loadJsonArray(j)
    val format = Json { ignoreUnknownKeys = true }

    /*val fromApi = format.decodeFromString<Trivia>(jsonArray[index].toString()).incorrect_answers

    //

    val list = mutableListOf<String>()

    for(fA in fromApi) {

        list.add(HtmlCompat.fromHtml(fA, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())
    }

    return list*/
    return format.decodeFromString<Trivia>(jsonArray[i].toString()).incorrect_answers
}
