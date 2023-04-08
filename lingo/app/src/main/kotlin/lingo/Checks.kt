package lingo

import java.io.File
import kotlin.system.exitProcess

object Checks {

    fun isFiveLetter(s: String): Boolean {

        return when(s.length) {

            5 -> true
            else -> false
        }
    }

    fun isStringOnlyAlphabet(s: String): Boolean {

        s.forEach {

            if (!it.isLatin()) {
                return false
            }
        }

        return true
    }

    private fun Char.isLatin(): Boolean {
        return this in 'A'..'Z' || this in 'a'..'z'
    }

    fun hasUniqueCharacters(str: String): Boolean {

        val charSet = mutableSetOf<Char>()
        var hasDuplicateChar = false

        str.forEach { char ->

            if (charSet.contains(char)) {
                hasDuplicateChar = true
                return@forEach
            }

            charSet.add(char)
        }

        return !hasDuplicateChar
    }

    //

    fun checkCandidates(words: List<String>, candidates: List<String>): Int {

        val wordsSet = words.toSet()

        var count = 0

        for (candidate in candidates) {

            if (!wordsSet.contains(candidate)) {

                if(candidate.isNotBlank()) count++
            }
        }

        return count
    }

    fun checkFile(f: String, candidate: Boolean): List<String> {

        val words: List<String>

        try {

            words = File(f).readLines()

        } catch(e: Exception) {

            if(!candidate) {

                println("Error: The words file $f doesn't exist.")

            } else {

                println("Error: The candidate words file $f doesn't exist.")
            }

            exitProcess(-1)
        }

        words.forEach { check(it) }

        return words
    }

    private fun check(s: String) {

        var c: Boolean = isFiveLetter(s)

        if(!c) { invalid++; println(s); return }

        c = isStringOnlyAlphabet(s)

        if(!c) { invalid++; println(s); return }

        c = hasUniqueCharacters(s)

        if(!c) { invalid++; println(s); return }
    }
}
