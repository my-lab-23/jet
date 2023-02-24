package com.example.trivia

import java.io.*

fun save(inputText: String, fileName: String, mode: Int) {

    val fileContext = MyApplication.appContext

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

fun load(s: String): String {

    val fileContext = MyApplication.appContext

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
