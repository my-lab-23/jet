package lingo

object PrintColored {

    fun printColoredHint(hint: String, candy: String) {

        var i = 0

        candy.forEach { c ->

            when(hint[i]) {

                in 'A'..'Z' -> printGreen(c.uppercaseChar())
                in 'a'..'z' -> printYellow(c.uppercaseChar())
                '_' -> printGrey(c.uppercaseChar())
            }

            i++
        }

        print("\n")
    }

    fun printRed(s: String) {

        println("\n\u001B[30;101m$s\u001B[0m")
    }

    fun printGreen(c: Char) {

        print("\u001B[30;102m$c\u001B[0m")
    }

    private fun printYellow(c: Char) {

        print("\u001B[30;43m$c\u001B[0m")
    }

    private fun printGrey(c: Char) {

        print("\u001B[30;107m$c\u001B[0m")
    }
}
