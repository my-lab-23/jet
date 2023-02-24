package it.emanueleweb.cardgame.logic

import it.emanueleweb.cardgame.*

var index = -1
var wins1 = 0
var wins2 = 0

val mazzo = Mazzo()
val giocatore = Giocatore()
val computer = Giocatore()

fun game() {

    mazzo.mescolaMazzo()

    manoGiocatoreUI = giocatore.prendi3(mazzo)
    manoComputerUI = computer.prendi3(mazzo)

    cardToRes()

    var giocatoreValue: Int
    var computerValue: Int

    while(true) {

        giocatore.printMano("Giocatore")
        computer.printMano("Computer")
        giocatoreValue = playGiocatore(); selCard = -1
        computerValue = playComputer(giocatoreValue)
        mano(giocatoreValue, computerValue)
        println("\nGiocatore: $wins1 - $wins2 :Computer\n")

        //

        pause = true
        while(pause) { /* Do nothing */ }

        giocatore.prendi1(mazzo)
        computer.prendi1(mazzo)

        cardToRes()

        //

        giocatore.printMano("Giocatore")
        computer.printMano("Computer")
        computerValue = playComputer()
        giocatoreValue = playGiocatore(); selCard = -1
        mano(giocatoreValue, computerValue)
        println("\nGiocatore: $wins1 - $wins2 :Computer\n")

        //

        pause = true
        while(pause) { /* Do nothing */ }

        giocatore.prendi1(mazzo)
        computer.prendi1(mazzo)

        cardToRes()

        //

        selComputerCardUI = -1
    }
}

fun playGiocatore(): Int {

    while (true) {

        try {

            //index = readln().toInt()
            //index = giocatore.scegli(strategia = 1) + 1
            index = selCard
            if (index in 1..3) break

        } catch (e: Exception) {

            println("Errore, riprova!")
        }
    }

    return giocatore.gioca1(index - 1)
}

fun playComputer(giocatoreValue: Int = -1): Int {

    index = if(giocatoreValue==-1) {
        computer.scegli(strategia = 2, giocatoreValue)
    } else {
        computer.scegli(strategia = 3, giocatoreValue)
    }

    selComputerCardUI = index

    val value = computer.gioca1(index)
    println("Valore giocato dal computer: $value")
    return value
}

fun mano(card1Value: Int, card2Value: Int) {

    if (card1Value > card2Value) {
        wins1++
    }
    else if (card1Value < card2Value) {
        wins2++
    }
}
