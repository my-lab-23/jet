package com.example.kata

object MyData {

    data class App(val name: String, val description: String, val packageName: String)

    val listApp = mutableListOf(

        App("Bibbia", "App che mostra un versetto della Bibbia ricavato con Jsoup.", "com.example.bibbia"),
        App("Jalaspot", "Progetto app Android per Jalaspot con Compose e NavController.", "com.example.jalaspot"),
        App("Logo", "Progetto interprete Logo per Android con Compose e Canvas.", "com.example.logo"),
        App("Percival", "Progetto Simon Says game per Android.", "com.example.percival"),
        App("Rosario", "App per la recita del Rosario (Compose Android).", "com.example.rosariocompose"),
        App("Stanza", "Versione Android di \"rubrica\" con Room al posto di Exposed.", "com.example.stanza"),
        App("Trivia", "Progetto app per quiz presi da Open Trivia DB con ViewModel e StateFlow.", "com.example.trivia"),
        App("Immagina", "Cliccando su ROADS o SEA visualizza delle immagini casuali prese da Unsplash.", "com.example.immagina"),
        App("Scrivi", "Scrive i messaggi (Compose Android).", "com.example.scrivi"),
        App("Colordata", "Scrive i messaggi (Compose Android).", "com.example.colordata"),
        App("Sensors", "Lista i sensori di un dispositivo Android.", "com.example.sensors"),
        App("Rai", "Stasera in TV.", "com.example.rai")
    )
}
