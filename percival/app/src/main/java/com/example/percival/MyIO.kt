package com.example.percival

import android.content.Context
import java.io.*

object MyIO {

    fun save(fileContext: Context, inputText: String, fileName: String, mode: Int) {
        try {
            val output = fileContext.openFileOutput(fileName, mode)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(inputText)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun load(fileContext: Context, s: String): String {
        val content = StringBuilder()
        try {
            val input = fileContext.openFileInput(s)
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }
}
