package com.example.plugins

import com.example.DataO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {

    @Suppress("UNUSED_VARIABLE")
    val database = Database.connect(

        "jdbc:postgresql://localhost:5432/expo",
        driver = "org.postgresql.Driver",
        user = "expo",
        password = "expo"
    )

    fun resetTableData() {

        SchemaUtils.drop(DataO)
        SchemaUtils.create(DataO)
    }

    routing {

        get("/crudites/resetData") {

            transaction { resetTableData() }
            call.respond(HttpStatusCode.OK)
        }
    }
}
