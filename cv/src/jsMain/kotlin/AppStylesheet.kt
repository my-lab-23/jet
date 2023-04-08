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
        // Font size
        fontSize(20.px)
    }

    val pari by style {
        backgroundColor(Color.beige)
    }

    val dispari by style {
        //backgroundColor(Color("#ABCDEF"))
        backgroundColor(Color("#D9EAD3"))
    }

    val pdf by style {
        backgroundColor(Color("#fcb7b4"))
    }

    val button by style {
        backgroundColor(Color.lightgray)
        border(1.px, LineStyle.Solid, Color.lightgray)
        // Border radius
        borderRadius(20.px)
        padding(10.px)
        // Font size
        fontSize(20.px)
    }
}
