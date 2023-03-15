package com.example.scrivi

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
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
}
