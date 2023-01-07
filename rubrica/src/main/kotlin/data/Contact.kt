package data

data class Contact(val nome: String, val cognome: String, val telefono: String)

val contacts = mutableListOf<Contact>()
var contactToEdit = Contact("", "", "")
