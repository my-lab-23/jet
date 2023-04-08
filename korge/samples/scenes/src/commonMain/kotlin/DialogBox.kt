import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors

class DialogBox(
    width: Double,
    height: Double,
    text: String
) : Container() {
    private val background = solidRect(width, height, Colors.WHITE)
    private val message = text(text, textSize = 32.0, color = Colors.BLACK)
    private val closeText = text("X", textSize = 32.0, color = Colors.RED)

    init {
        addChild(background)
        addChild(message)
        addChild(closeText)

        message.position(width / 2 - message.width / 2, height / 2 - message.height / 2)
        // Position the close text in the top right corner of the dialog box
        closeText.position((width - closeText.width -10).toFloat(), 5.toFloat())

        closeText.onClick {
            removeChild(background)
            removeChild(message)
            removeChild(closeText)
        }
    }
}
