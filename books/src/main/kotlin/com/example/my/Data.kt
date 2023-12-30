package com.example.my

object Authors : Iterable<Pair<String, String>> {

    private val authors = mapOf(
        "Kerouak" to "OL21491A",
        "Severino" to "OL48545A",
        "Leibniz" to "OL116444A",
        "Tommaso" to "OL38887A",
        "Aristotele" to "OL22105A",
        "Schopenhauer" to "OL119580A",
        "Leopardi" to "OL45982A",
        "Cioran" to "OL145170A",
        "Dostoevskij" to "OL22242A",
        "Proust" to "OL120205A",
        "Keats" to "OL153488A",
        "Dick" to "OL274606A"
    )

    override fun iterator(): Iterator<Pair<String, String>> {
        return authors.toList().iterator()
    }
}

object Composers : Iterable<Pair<String, String>> {

    private val composers = mapOf(
        "Haydn" to "208",
        "Handel" to "67",
        "Mozart" to "196",
        "Bach" to "87",
        "Beethoven" to "145",
        "Telemann" to "83",
        "Vivaldi" to "98",
        "Strauss" to "171",
        "Sibelius" to "186",
        "Messiaen" to "150",
        "Rossini" to "60",
        "Ravel" to "57"
    )

    override fun iterator(): Iterator<Pair<String, String>> {
        return composers.toList().iterator()
    }
}
