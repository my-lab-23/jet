package it.emanueleweb.cardgame

import it.emanueleweb.cardgame.logic.Card

var manoGiocatoreUI = mutableListOf<Card>()
var selGiocatoreCardUI = -1
val giocatoreCardsToRes = mutableListOf(-1, -1, -1)

var manoComputerUI = mutableListOf<Card>()
var selComputerCardUI = -1
val computerCardsToRes = mutableListOf(-1, -1, -1)

var selCard = -1
var pause = false

fun cardToRes() {

    var i = 0

    manoGiocatoreUI.forEach {

        when(it.seme) {

            "Cuori" -> giocatoreCardsToRes[i] = cuori(it.valore)
            "Quadri" -> giocatoreCardsToRes[i] = quadri(it.valore)
            "Fiori" -> giocatoreCardsToRes[i] = fiori(it.valore)
            "Picche" -> giocatoreCardsToRes[i] = picche(it.valore)
        }

        i++
    }

    i = 0

    manoComputerUI.forEach {

        when(it.seme) {

            "Cuori" -> computerCardsToRes[i] = cuori(it.valore)
            "Quadri" -> computerCardsToRes[i] = quadri(it.valore)
            "Fiori" -> computerCardsToRes[i] = fiori(it.valore)
            "Picche" -> computerCardsToRes[i] = picche(it.valore)
        }

        i++
    }
}

fun picche(valore: Int): Int {

    when(valore) {

        1 -> return R.drawable.ace_of_spades
        2 -> return R.drawable.c2_of_spades
        3 -> return R.drawable.c3_of_spades
        4 -> return R.drawable.c4_of_spades
        5 -> return R.drawable.c5_of_spades
        6 -> return R.drawable.c6_of_spades
        7 -> return R.drawable.c7_of_spades
        8 -> return R.drawable.c8_of_spades
        9 -> return R.drawable.c9_of_spades
        10 -> return R.drawable.c10_of_spades
        11 -> return R.drawable.jack_of_spades2
        12 -> return R.drawable.queen_of_spades2
        13 -> return R.drawable.king_of_spades2
    }

    return -1

}

fun fiori(valore: Int): Int {

    when(valore) {

        1 -> return R.drawable.ace_of_clubs
        2 -> return R.drawable.c2_of_clubs
        3 -> return R.drawable.c3_of_clubs
        4 -> return R.drawable.c4_of_clubs
        5 -> return R.drawable.c5_of_clubs
        6 -> return R.drawable.c6_of_clubs
        7 -> return R.drawable.c7_of_clubs
        8 -> return R.drawable.c8_of_clubs
        9 -> return R.drawable.c9_of_clubs
        10 -> return R.drawable.c10_of_clubs
        11 -> return R.drawable.jack_of_clubs2
        12 -> return R.drawable.queen_of_clubs2
        13 -> return R.drawable.king_of_clubs2
    }

    return -1
}

fun quadri(valore: Int): Int {

    when(valore) {

        1 -> return R.drawable.ace_of_diamonds
        2 -> return R.drawable.c2_of_diamonds
        3 -> return R.drawable.c3_of_diamonds
        4 -> return R.drawable.c4_of_diamonds
        5 -> return R.drawable.c5_of_diamonds
        6 -> return R.drawable.c6_of_diamonds
        7 -> return R.drawable.c7_of_diamonds
        8 -> return R.drawable.c8_of_diamonds
        9 -> return R.drawable.c9_of_diamonds
        10 -> return R.drawable.c10_of_diamonds
        11 -> return R.drawable.jack_of_diamonds2
        12 -> return R.drawable.queen_of_diamonds2
        13 -> return R.drawable.king_of_diamonds2
    }

    return -1
}

fun cuori(valore: Int): Int {

    when(valore) {
        
        1 -> return R.drawable.ace_of_hearts
        2 -> return R.drawable.c2_of_hearts
        3 -> return R.drawable.c3_of_hearts
        4 -> return R.drawable.c4_of_hearts
        5 -> return R.drawable.c5_of_hearts
        6 -> return R.drawable.c6_of_hearts
        7 -> return R.drawable.c7_of_hearts
        8 -> return R.drawable.c8_of_hearts
        9 -> return R.drawable.c9_of_hearts
        10 -> return R.drawable.c10_of_hearts
        11 -> return R.drawable.jack_of_hearts2
        12 -> return R.drawable.queen_of_hearts2
        13 -> return R.drawable.king_of_hearts2
    }
    
    return -1
}
