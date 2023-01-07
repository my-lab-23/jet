package db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

import data.Contact
import data.contacts

fun connectDB() {

    Database.connect("jdbc:postgresql://localhost:5432/expo", driver = "org.postgresql.Driver",
        user = "expo", password = "expo")
}

fun resetTable() {

    SchemaUtils.drop(ContactTable)
    SchemaUtils.create(ContactTable)
}

fun createRow(nomeL: String, cognomeL: String, telefonoL: String) {

    val id = ContactTable.insertAndGetId {
        it[nome] = nomeL
        it[cognome] = cognomeL
        it[telefono] = telefonoL
    }

    println(id.value)
}

fun deleteRow(contact: Contact) {

    // Delete where
    // nome = contact.nome and
    // cognome = contact.cognome and
    // telefono = contact.telefono
    ContactTable.deleteWhere {
        (nome eq contact.nome) and
        (cognome eq contact.cognome) and
        (telefono eq contact.telefono)
    }
}

fun loadInContacts() {

    transaction {
        ContactTable.selectAll().forEach {
            contacts.add(Contact(
                it[ContactTable.nome],
                it[ContactTable.cognome],
                it[ContactTable.telefono]))
        }
    }
}

private object ContactTable: IntIdTable() {

    val nome = varchar("nome", 100)
    val cognome = varchar("cognome", 100)
    val telefono = varchar("telefono", 100)
}
