package com.example

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Data(val id: Int, val data: String)

object DataO : IntIdTable() {
    val myID = integer("myID").uniqueIndex()
    val data = varchar("data", 500)
}

class DataC(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DataC>(DataO)
    var myID by DataO.myID
    var data by DataO.data
}
