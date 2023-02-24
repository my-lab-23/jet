package com.example.stanza.data

import com.example.stanza.db.ContactDB

data class Contact(val nome: String, val cognome: String, val telefono: String)

val contacts = mutableListOf<Contact>()
var contactToEdit = Contact("", "", "")

fun fromContactToContactDB(contact: Contact): ContactDB {
    return ContactDB(contact.nome, contact.cognome, contact.telefono)
}

fun fromContactDBToContact(contactDB: ContactDB): Contact {
    return Contact(contactDB.nome, contactDB.cognome, contactDB.telefono)
}
