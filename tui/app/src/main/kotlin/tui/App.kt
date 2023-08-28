package tui

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal

fun main() {

    val terminal = DefaultTerminalFactory().createTerminal()
    val screen: Screen = TerminalScreen(terminal)
    screen.startScreen()

    val panel = Panel()
    panel.setLayoutManager(GridLayout(1))

    val txtNum1 = TextBox("jar")
    txtNum1.layoutData = GridLayout.createHorizontallyFilledLayoutData()
    panel.addComponent(txtNum1)

    //

    Button("Cerca!") {

        clear(terminal)

        val textGraphics0 = terminal.newTextGraphics()

        val text = txtNum1.getTextOrDefault("Non ha inserito il testo da cercare!")
        val s = getProcessesContainingString(text, terminal.terminalSize.columns-3)

        textGraphics0.foregroundColor = TextColor.ANSI.BLACK
        textGraphics0.backgroundColor = TextColor.ANSI.WHITE

        s.forEachIndexed { index, it ->
            val i = index + 1
            textGraphics0.putString(3, 4+i, it, SGR.BOLD)
        }

        txtNum1.text = ""

    }.addTo(panel)

    //

    Button("Uccidi!") {

        clear(terminal)

        val textGraphics1 = terminal.newTextGraphics()

        val text = txtNum1.getTextOrDefault("Non hai inserito il processo da uccidere!")
        val s = "Ho ucciso il processo $text"

        textGraphics1.foregroundColor = TextColor.ANSI.BLACK
        textGraphics1.backgroundColor = TextColor.ANSI.WHITE

        textGraphics1.putString(3, 5, s, SGR.BOLD)

        uccidi(text)

    }.addTo(panel)

    //

    val window = BasicWindow()
    window.setHints(listOf(Window.Hint.FULL_SCREEN))
    window.component = panel

    val gui = MultiWindowTextGUI(screen, DefaultWindowManager(), EmptySpace(TextColor.ANSI.BLUE))
    gui.addWindowAndWait(window)
}

fun getProcessesContainingString(searchString: String, maxLength: Int): List<String> {
    val processBuilder = ProcessBuilder("ps", "aux")
    val process = processBuilder.start()

    val inputStream = process.inputStream
    val bufferedReader = inputStream.bufferedReader()

    val result = mutableListOf<String>()

    bufferedReader.useLines { lines ->
        lines.filter { it.contains(searchString) }
            .forEach { line ->
                val truncatedLine = truncateProcessInfo(line, maxLength, searchString)
                result.add(truncatedLine)
            }
    }

    return result
}

fun truncateProcessInfo(input: String, length: Int, searchString: String): String {
    val fields = input.split("\\s+".toRegex())

    if (fields.size < 11) {
        return "Invalid input format."
    }

    val pid = fields[1]
    val user = fields[0]

    val maxLength = length - pid.length - user.length - 9

    val commandIndex = fields.drop(10).indexOfFirst { it.contains(searchString) }
    val command = if (commandIndex != -1) {
        fields[commandIndex + 10].substring(0, maxLength.coerceAtMost(fields[commandIndex + 10].length))
    } else {
        fields[10].substring(0, maxLength.coerceAtMost(fields[10].length))
    }

    return "$pid - $user - $command"
}

fun clear(terminal: Terminal) {

    val textGraphics = terminal.newTextGraphics()

    textGraphics.foregroundColor = TextColor.ANSI.BLACK
    textGraphics.backgroundColor = TextColor.ANSI.WHITE
    val rows = terminal.terminalSize.rows-3
    val columns = terminal.terminalSize.columns-3

    for(r in 5..rows) {
        for(c in 3..columns) {
            textGraphics.putString(c, r, " ")
        }
    }
}

fun uccidi(text: String) {

    val pid = text.toInt()
    val processBuilder = ProcessBuilder("kill", "-9", pid.toString())
    processBuilder.start()
}
