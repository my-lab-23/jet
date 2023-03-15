import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Serializable
data class MyMessage(
    val msg: String,
    val timestamp: Long = Instant.now().toEpochMilli()
) {

    fun getJsonObject(): JsonObject {
        return Json.encodeToJsonElement(this).jsonObject
    }

    fun toJsonString(): String {
        val json = Json { prettyPrint = true }
        return json.encodeToString(serializer(), this)
    }

    companion object {
        fun fromJsonString(jsonString: String): MyMessage {
            val json = Json { ignoreUnknownKeys = true }
            return json.decodeFromString(serializer(), jsonString)
        }

        fun timestampToString(timestamp: Long): String {
            val dateFormat = SimpleDateFormat("HH:mm - dd-MM-yyyy", Locale.getDefault())
            val date = Date(timestamp)
            return dateFormat.format(date)
        }
    }
}
