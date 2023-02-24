package it.emanueleweb.cardgame.logic

import kotlin.random.Random

class Giocatore {

    private val mano = mutableListOf<Card>()

    fun prendi3(mazzo: Mazzo): MutableList<Card> {

        mano.add(mazzo.distribuisci())
        mano.add(mazzo.distribuisci())
        mano.add(mazzo.distribuisci())
        return mano
    }

    fun prendi1(mazzo: Mazzo) {

        mano.add(mazzo.distribuisci())
    }

    fun gioca1(index: Int): Int {

        val cardValue = mano[index].valore
        mano.removeAt(index)
        return cardValue
    }

    fun printMano(nome: String) {

        print("$nome: ")

        print(" ${mano[0].valore} di ${mano[0].seme} ")
        print(" ${mano[1].valore} di ${mano[1].seme} ")
        print(" ${mano[2].valore} di ${mano[2].seme} ")

        print("\n")
    }

    fun scegli(strategia: Int, giocatoreValue: Int = -1): Int {

        when(strategia) {

            0 -> return 0
            1 -> return Random.nextInt(0, 2)
            2 -> return mano.indices.maxBy { mano[it].valore }
            3 -> return findCardIndex(giocatoreValue)
        }

        return -1
    }

    private fun findCardIndex(value: Int): Int {
        var foundCard: Card? = null
        var foundIndex = -1
        for (i in mano.indices) {
            val card = mano[i]
            if (card.valore > value) {
                if (foundCard == null || card.valore < foundCard.valore) {
                    foundCard = card
                    foundIndex = i
                }
            }
        }
        return foundIndex.takeIf { it != -1 } ?: mano.indexOf(mano.minBy { it.valore })
    }
}
