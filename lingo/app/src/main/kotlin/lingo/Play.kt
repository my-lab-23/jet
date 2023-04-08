package lingo

import lingo.PrintColored.printRed
import lingo.PrintColored.printColoredHint
import lingo.PrintColored.printGreen
import java.time.Instant
import kotlin.system.exitProcess

object Play {

    private var candy = ""
    private var attemps = 0
    private val start: Long = Instant.now().epochSecond
    private var end: Long = 0
    private val hints = mutableListOf<String>()
    private val wordys = mutableListOf<String>()
    private var underScore = mutableListOf<Char>()

    fun play(candidates: List<String>, words: List<String>) {

        if(candy =="") candy = chooseSecretWord(candidates)

        println("Input a 5-letter word:")
        val wordy = readln()
        attemps++

        if(wordy=="exit") {

            println("The game is over.")
            println(candy)
            exitProcess(-1)

        } else if(wordy== candy) {

            if(attemps ==1) {

                candy.forEach {

                    printGreen(it.uppercaseChar())
                }

                println("\nCorrect!")
                println("Amazing luck! The solution was found at once.")

            } else {

                aux(wordy)

                //

                end = Instant.now().epochSecond
                val time = end - start
                println("Correct!")
                println("The solution was found after $attemps" +
                        " tries in $time seconds.")
            }

            exitProcess(-1)
        }

        var check = isValid(wordy)

        if(!words.contains(wordy) && check=="ok") {

            check = "The input word isn't included in my words list."
        }

        if(check=="ok") {

            val hint = aux(wordy)

            //

            val xyz = replaceUnderscores(hint, wordy)
            addToMutableListAndSort(xyz)
            printRed(underScore.joinToString(""))
        }
        else { println(check) }
    }

    private fun aux(wordy: String): String {

        val hint = hint(wordy, candy)
        hints.add(hint)
        wordys.add(wordy)

        var i = 0

        hints.forEach {

            printColoredHint(it, wordys[i])
            i++
        }

        return hint
    }

    private fun addToMutableListAndSort(str: String) {

        for (char in str) { underScore.add(char.uppercaseChar()) }
        underScore = underScore.distinct().toMutableList()
        underScore.sort()
    }

    private fun replaceUnderscores(underscored: String, plain: String): String {

        val result = StringBuilder()

        for (i in underscored.indices) {

            if (underscored[i] == '_') {

                result.append(plain[i].uppercaseChar())
            } else {

                //result.append(underscoredString[i])
            }
        }

        return result.toString()
    }

    private fun hint(wordy: String, candy: String): String {

        return clueString(wordy.toList(), candy.toList())
    }

    private fun clueString(wordUser: List<Char>, wordCand: List<Char>): String {

        var str = ""

        for (i in 0..4) {

            str += when {

                wordCand[i] == wordUser[i] -> wordUser[i].uppercaseChar()
                wordCand.contains(wordUser[i]) -> wordUser[i]
                else -> '_'
            }
        }

        return str
    }

    private fun isValid(s: String): String {

        var c: Boolean = Checks.isFiveLetter(s)

        if(!c) { return "The input isn't a 5-letter word." }

        c = Checks.isStringOnlyAlphabet(s)

        if(!c) { return "One or more letters of the input aren't valid." }

        c = Checks.hasUniqueCharacters(s)

        if(!c) { return "The input has duplicate letters." }

        return "ok"
    }

    private fun chooseSecretWord(candidates: List<String>): String {

        return candidates.random()
    }
}
