package lingo

import lingo.Checks.checkCandidates
import lingo.Checks.checkFile
import lingo.Play.play
import java.util.*
import kotlin.system.exitProcess

var invalid = 0

fun main() {

    val wordsFile = System.getenv("WORDS_FILE")
    val candyFile = System.getenv("CANDY_FILE")

    var words = checkFile(wordsFile, false)
    printInvalid(wordsFile)

    invalid = 0

    var candidates = checkFile(candyFile, true)
    printInvalid(candyFile)

    words = toLowerCase(words)
    candidates = toLowerCase(candidates)

    val n = checkCandidates(words, candidates)

    if(n!=0 && n!= invalid) {

        println("Error: $n candidate words are not included in " +
                "the $candyFile file.")
        exitProcess(-1)
    }

    //

    println("Words Virtuoso")

    while(true) { play(candidates, words) }
}

fun toLowerCase(strings: List<String>): List<String> {
    return strings.map { it.lowercase(Locale.getDefault()) }
}

fun printInvalid(f: String) {

    if(invalid !=0) {

        println("Error: $invalid invalid words were found in the $f file.")
        exitProcess(-1)
    }
}
