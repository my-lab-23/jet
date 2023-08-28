package com.example

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object SqlCrud {

    fun create(newId: Int, newData: String) { DataO.insert { it[myID] = newId; it[data] = newData } }

    fun read(searchId: Int): Query { return DataO.select { DataO.myID eq searchId } }

    fun list(): Query { return DataO.selectAll() }

    fun update(searchId: Int, newData: String) {

        val q = DataO.select { DataO.myID eq searchId }
        q.forEach { it[DataO.data] = newData }
    }

    fun delete(searchId: Int) { DataO.deleteWhere { myID eq searchId } }
}

object SqlCrudString {

    fun read(searchId: Int): String {

        val q = DataO.select { DataO.myID eq searchId }
        val l = aux(q)
        return l.joinToString()
    }

    fun list(): String {

        val q = DataO.selectAll()
        val l = aux(q)
        return l.joinToString()
    }

    private fun aux(q: Query): MutableList<String> {

        val l = mutableListOf<String>()

        q.forEach {
            val id = it[DataO.myID]
            val data = it[DataO.data]
            val s = "{ \"id\": ${id}, \"data\": \"${data}\" }"
            l.add(s)
        }

        return l
    }
}