package com.example.plugins

import com.example.Crud
import com.example.CrudString
import com.example.Data
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {

    routing {

        authenticate {

            get("crudites/data/{id}") {

                //TestCrud.read(call)

                val id = call.parameters["id"]
                val data = transaction { CrudString.read(id!!.toInt()) }
                call.respond(data)
            }

            get("crudites/data/list") {

                //TestCrud.list(call)

                val data = transaction { CrudString.list() }
                call.respond(data)
            }

            post("/crudites/data") {

                //TestCrud.create(call)

                val data = call.receive<Data>()
                val check = transaction { CrudString.read(data.id) }

                if (check!="") {

                    call.respondText("Error: ID already in use", status = HttpStatusCode.Conflict)

                } else {
                    transaction { Crud.create(data.id, data.data) }
                    call.respondText("Data stored correctly", status = HttpStatusCode.Created)
                }
            }

            put("/crudites/data/{id}") {

                //TestCrud.update(call)

                val id = call.parameters["id"]?.toInt()
                val data = call.receive<Data>()
                val check = transaction { CrudString.read(data.id) }

                if (check!="") {

                    transaction { Crud.update(id!!, data.data) }
                    call.respondText("Data updated correctly", status = HttpStatusCode.OK)

                } else {
                    call.respondText("Error: Data with the provided ID not found", status = HttpStatusCode.NotFound)
                }
            }

            delete("/crudites/data/{id}") {

                //TestCrud.delete(call)

                val id = call.parameters["id"]?.toInt()
                val check = transaction { CrudString.read(id!!) }

                if (check!="") {

                    transaction { Crud.delete(id!!) }
                    call.respondText("Data deleted correctly", status = HttpStatusCode.OK)

                } else {
                    call.respondText("Error: Data with the provided ID not found", status = HttpStatusCode.NotFound)
                }
            }
        }
    }
}
