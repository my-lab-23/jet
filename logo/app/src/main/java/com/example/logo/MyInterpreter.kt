package com.example.logo

fun interpreter(command: String): MyInput? {

    val temp = command.split(" ")
    val cmd = temp[0]

    val value = try {
        temp[1].toFloat()
    } catch(e: IndexOutOfBoundsException) {
        0f
    }

    when(cmd.uppercase()) {

        "FD"          -> return forward(-value)
        "FORWARD"     -> return forward(-value)
        "BK"          -> return forward(value)
        "BACK"        -> return forward(value)

        // Modulo 360°

        "RT"          -> MyCommands.angle += -value
        "RIGHT"       -> MyCommands.angle += -value
        "LT"          -> MyCommands.angle += value
        "LEFT"        -> MyCommands.angle += value

        //

        "CS"          -> return null
        "CLEARSCREEN" -> return null
        "HOME"        -> MyCommands.lastInput = MyCommands.centerInput
    }

    return MyCommands.lastInput
}
