import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.renderComposable
import ui.myTable

fun main() {

    renderComposable(rootElementId = "root") {

        Style(AppStylesheet)
        myTable()
    }
}
