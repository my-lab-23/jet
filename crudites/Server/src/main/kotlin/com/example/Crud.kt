package com.example

import org.jetbrains.exposed.sql.SizedIterable

object Crud {

    fun create(newId: Int, newData: String) { DataC.new { myID = newId; data = newData } }

    fun read(searchId: Int): SizedIterable<DataC> { return DataC.find { DataO.myID eq searchId } }

    fun list(): SizedIterable<DataC> { return DataC.all() }

    fun update(searchId: Int, newData: String) {

        val sI = DataC.find { DataO.myID eq searchId }
        sI.forEach { it.data = newData }
    }

    fun delete(searchId: Int) {

        val sI = DataC.find { DataO.myID eq searchId }
        sI.forEach { it.delete() }
    }
}

//

object CrudString {

    fun read(searchId: Int): String {

        val sI = DataC.find { DataO.myID eq searchId }
        val l = aux(sI)
        return l.joinToString()
    }

    fun list(): String {

        val sI = DataC.all()
        val l = aux(sI)
        return l.joinToString()
    }

    private fun aux(sI: SizedIterable<DataC>): MutableList<String> {

        val l = mutableListOf<String>()

        sI.forEach {
            val id = it.myID
            val data = it.data
            val s = "{ \"id\": ${id}, \"data\": \"${data}\" }"
            l.add(s)
        }

        return l
    }
}
