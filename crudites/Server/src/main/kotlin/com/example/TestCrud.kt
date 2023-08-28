package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

object TestCrud {

    private val dataStorage = mutableListOf<Data>()

    suspend fun read(call: ApplicationCall) {

        val id = call.parameters["id"]
        val data = dataStorage.find { it.id == id!!.toInt() }!!
        call.respond(data)
    }

    suspend fun list(call: ApplicationCall) { call.respond(dataStorage) }

    suspend fun create(call: ApplicationCall) {

        val data = call.receive<Data>()

        if (dataStorage.any { it.id == data.id }) {

            call.respondText("Error: ID already in use", status = HttpStatusCode.Conflict)

        } else {
            dataStorage.add(data)
            call.respondText("Data stored correctly", status = HttpStatusCode.Created)
        }
    }

    suspend fun update(call: ApplicationCall) {

        val id = call.parameters["id"]?.toInt()
        val data = call.receive<Data>()

        val existingData = dataStorage.find { it.id == id }
        if (existingData != null) {

            dataStorage.remove(existingData)
            id?.let { it1 -> data.copy(id = it1) }?.let { it2 -> dataStorage.add(it2) }
            call.respondText("Data updated correctly", status = HttpStatusCode.OK)

        } else {
            call.respondText("Error: Data with the provided ID not found", status = HttpStatusCode.NotFound)
        }
    }

    suspend fun delete(call: ApplicationCall) {

        val id = call.parameters["id"]?.toInt()
        val existingData = dataStorage.find { it.id == id }

        if (existingData != null) {

            dataStorage.remove(existingData)
            call.respondText("Data deleted correctly", status = HttpStatusCode.OK)

        } else {
            call.respondText("Error: Data with the provided ID not found", status = HttpStatusCode.NotFound)
        }
    }
}
