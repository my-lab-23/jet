package it.emanueleweb.cardgame.logic

class Mazzo {

    private val mazzo = mutableListOf<Card>()
    private var index = -1

    init {

        populate("Cuori")
        populate("Quadri")
        populate("Fiori")
        populate("Picche")
    }

    private fun populate(seme: String) {

        var i = 0

        repeat(13) {

            i++
            val card = Card(seme, i)
            mazzo.add(card)
        }
    }

    //

    fun mescolaMazzo() {

        mazzo.shuffle()
    }

    fun distribuisci(): Card {

        if(index<51) { index++ } else {

            index = 0
            mazzo.shuffle()
        }

        return mazzo[index]
    }
}
