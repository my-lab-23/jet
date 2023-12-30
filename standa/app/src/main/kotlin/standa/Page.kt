package standa

import java.io.File

object Page: Shared() {

    const val NAME0 = "Part1"
    const val NAME1 = "Part2"
    const val NAME2 = "Part3"

    private val lorem = File("ws/data/lorem.txt").readText()

    fun generatePage(pageNumber: Int) {

        when (pageNumber) {

            0 -> {
                url = zero[0]
                active = LinkData(zero[0], zero[1])
                inactive = listOf(LinkData(uno[0], uno[1]), LinkData(due[0], due[1]))
                nextLink = uno[0]

                val codeDetail0 = CodeData(
                    lorem,
                    "language-kotlin", "1",
                    "https://raw.githubusercontent.com/my-lab-23/jet/main/standa/app/src/main/kotlin/standa/App.kt"
                )
                val codeDetail1 = CodeData(
                    lorem,
                    "language-kotlin", "2",
                    "https://raw.githubusercontent.com/my-lab-23/jet/main/standa/app/src/main/kotlin/standa/Aux.kt"
                )
                codeDetails = listOf(codeDetail0, codeDetail1)
            }

            1 -> {
                url = uno[0]
                active = LinkData(uno[0], uno[1])
                inactive = listOf(LinkData(zero[0], zero[1]), LinkData(due[0], due[1]))
                nextLink = due[0]

                val codeDetail0 = CodeData(
                    lorem,
                    "language-kotlin", "1",
                    "https://raw.githubusercontent.com/my-lab-23/jet/main/standa/app/src/main/kotlin/standa/Page.kt"
                )
                codeDetails = listOf(codeDetail0)
            }

            2 -> {
                url = due[0]
                active = LinkData(due[0], due[1])
                inactive = listOf(LinkData(zero[0], zero[1]), LinkData(uno[0], uno[1]))
                nextLink = zero[0]

                val codeDetail0 = CodeData(
                    lorem,
                    "language-handlebars", "1",
                    "https://raw.githubusercontent.com/my-lab-23/jet/main/standa/ws/hbs/index.hbs"
                )
                codeDetails = listOf(codeDetail0)
            }
        }
    }
}
