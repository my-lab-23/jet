package com.example.plugins

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.text.SimpleDateFormat

object MyDB {

    fun connectDB() {
        val host = System.getenv("PG_HOST")
        val port = System.getenv("PG_PORT")
        val database = System.getenv("PG_DATABASE")
        val user = System.getenv("PG_USER")
        val password = System.getenv("PG_PASSWORD")

        val url = "jdbc:postgresql://$host:$port/$database"
        Database.connect(url, driver = "org.postgresql.Driver", user = user, password = password)
    }

    fun resetTable() {

        SchemaUtils.drop(MsgTable)
        SchemaUtils.create(MsgTable)
    }

    fun createRow(msg: String, timeStamp: Long) {

        try {

            MsgTable.insert {
                it[MsgTable.msg] = msg
                it[MsgTable.timeStamp] = timeStamp
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    object MsgTable : IntIdTable() {

        val msg = text("msg")
        val timeStamp = long("timeStamp")
    }

    fun searchByTimeStamp(timeStamp: Long) {
        val result = MsgTable.select { MsgTable.timeStamp eq timeStamp }
        result.forEach { row ->

            val msg = row[MsgTable.msg]
            println(msg)
        }
    }

    fun searchLastNMsgT(n: Int): String {

        return transaction {
            searchLastNMsg(n).joinToString(separator = "\n")
        }.run { this }
    }

    fun searchLastNMsg(n: Int): List<String> {

        val res = mutableListOf<String>()

        val results = MsgTable.selectAll()
            .orderBy(MsgTable.timeStamp, SortOrder.DESC)
            .limit(n)
            .toList()

        results.reversed().forEach { row ->

            val msg = row[MsgTable.msg]
            println(msg)
            val r =  MySerialization
                .serialize(Msg(row[MsgTable.msg], row[MsgTable.timeStamp]))
            res.add(r)
        }

        return res
    }

    private fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
            println("\nLock file deleted\n")
        }
    }

    fun searchBetweenDate(date1: String, date2: String) {

        val startDate: Long = getTimeStamp(date1)
        val endDate: Long = getTimeStamp(date2)

        println(startDate)
        println(endDate)

        val results = MsgTable.select {

            (MsgTable.timeStamp greaterEq startDate) and (MsgTable.timeStamp lessEq endDate)

        }.orderBy(MsgTable.timeStamp, SortOrder.DESC).toList()

        results.reversed().forEach { row ->
            val msg = row[MsgTable.msg]
            println(msg)
        }
    }

    private fun getTimeStamp(dateString: String): Long {

        val format = SimpleDateFormat("dd-MM-yyyy")
        val date = format.parse(dateString)
        return date.time
    }
}
