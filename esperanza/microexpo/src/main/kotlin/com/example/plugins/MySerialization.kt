package com.example.plugins

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Msg(
    val msg: String,
    val timestamp: Long
)

object MySerialization {

    fun isValidFormat(input: String): Boolean {

        val pattern =
            // ???
            """^\{\s*"msg"\s*:\s*".*",\s*"timestamp"\s*:\s*\d+\s*\}$"""
                .toRegex()

        return pattern.matches(input.trim())
    }


    fun deserialize(input: String): Msg {

        return Json.decodeFromString(input)
    }

    fun serialize(msg: Msg): String {

        return Json.encodeToString(msg)
    }
}
