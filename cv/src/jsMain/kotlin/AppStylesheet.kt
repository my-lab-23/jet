import org.jetbrains.compose.web.css.*

object AppStylesheet : StyleSheet() {

    val column by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
    }

    val row by style {
        width(800.px)
        border(1.px, LineStyle.Solid, Color.black)
        padding(10.px)
    }

    val pari by style {
        backgroundColor(Color.beige)
    }

    val dispari by style {
        backgroundColor(Color("#ABCDEF"))
    }
}
