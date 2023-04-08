package com.example.plugins

import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlin.system.exitProcess

fun expo() {

    MyDB.connectDB()

    switch()

    val files = getFilePaths("/home/ktor/data")

    transaction {

        addLogger(StdOutSqlLogger)

        files.forEach { file ->

            val lines = File(file).readLines()

            lines.forEach { line ->

                val check = MySerialization.isValidFormat(line)

                if (check) {

                    val msg = MySerialization.deserialize(line)
                    MyDB.createRow(msg.msg, msg.timestamp)
                }
            }
        }
    }

    deleteAllFilesInFolder("/home/ktor/data")
}

fun getFilePaths(folderPath: String): List<String> {

    val folder = File(folderPath)
    val fileList = folder.listFiles()
    val filePaths = mutableListOf<String>()

    fileList?.forEach { file ->

        if (file.isFile) {

            filePaths.add(file.absolutePath)
        } else if (file.isDirectory) {

            filePaths.addAll(getFilePaths(file.absolutePath))
        }
    }

    return filePaths
}

fun deleteAllFilesInFolder(folderPath: String) {
    val folder = File(folderPath)

    if (folder.exists() && folder.isDirectory) {

        val files = folder.listFiles()

        for (file in files!!) {

            if (file.isFile) { file.delete() }
        }
    } else {
        println("La cartella specificata non esiste o non è una cartella.")
    }
}

fun switch() {

    val switch = System.getenv("EXPO_SWITCH")
    val timestamp = System.getenv("EXPO_TIMESTAMP")
    val n = System.getenv("EXPO_LAST_N")
    val date1 = System.getenv("EXPO_DATE_1")
    val date2 = System.getenv("EXPO_DATE_2")

    if(switch!=null) {

        when (switch) {

            "reset" -> {

                transaction { MyDB.resetTable() }
                exitProcess(0)
            }

            "timestamp" -> {

                transaction { MyDB.searchByTimeStamp(timestamp.toLong()) }
                exitProcess(0)
            }

            "last" -> {

                transaction { MyDB.searchLastNMsg(n.toInt()) }
                exitProcess(0)
            }

            "between" -> {

                transaction { MyDB.searchBetweenDate(date1, date2) }
                exitProcess(0)
            }

            else -> {}
        }
    }
}
