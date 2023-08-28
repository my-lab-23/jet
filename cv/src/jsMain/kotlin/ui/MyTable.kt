package ui

import AppStylesheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun myTable(cv: List<String>) {

    val style = AppStylesheet

    Div(attrs = { classes(style.column) }) {

        Div(attrs = { classes(style.row, style.pari) }) {

            P { Text(cv[0]) }
            H1 { Text(cv[1]) }
        }

        Div(attrs = { classes(style.row, style.dispari) }) {

            H2 { Text(cv[2]) }
            P { Text(cv[3]) }
            P { Text(cv[4]) }
            P { Text(cv[5]) }
        }

        Div(attrs = { classes(style.row, style.pari) }) {

            H2 { Text(cv[6]) }
            P { Text(cv[7]) }
        }

        Div(attrs = { classes(style.row, style.dispari) }) {

            H2 { Text(cv[9]) }
            P { Text(cv[10] + " " + cv[11]) }
            P { Text(cv[12] + " " + cv[13]) }
            A(attrs = { href(cv[14]) }) { Text(cv[14]) }
        }

        Div(attrs = { classes(style.row, style.pari) }) {

            H2 { Text(cv[15]) }
            esp0(cv)
            esp1(cv)
            esp2(cv)
            esp3(cv)
            esp4(cv)
        }

        Div(attrs = { classes(style.row, style.dispari) }) { istruzione(cv) }

        Div(attrs = { classes(style.row, style.pari) }) { comp(cv) }

        Div(attrs = { classes(style.row, style.dispari) }) {

            H2 { Text(cv[80]) }
            P { Text(cv[81]) }
        }

        Div(attrs = { classes(style.row, style.pdf) }) {

            H2 { Text("Per scaricare in formato PDF:") }
            A(attrs = { href("https://github.com/my-lab-23/jet/blob/master/cv/pdf/cv.pdf") }) {
                Text("https://github.com/my-lab-23/jet/blob/master/cv/pdf/cv.pdf") }
        }
    }
}

@Composable
fun switchButton(switch: MutableState<Int>) {

    Button(attrs = { classes(AppStylesheet.button)
            onClick {

                switch.value = if (switch.value == 0) 1 else 0
            }
        }) {

            if (switch.value == 0) Text("Mostra")
            else Text("Nascondi")
        }
}

@Composable
fun istruzione(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H2 { Text(cv[58]) }

    switchButton(switch)

    if(switch.value==1) {

        H3 { Text(cv[59]
            .replace(" Attestato Teoria delle Reti, Windows Server, Linux Server, Oracle ", "")) }
        P { Text(cv[59]
            .replace("Aprile-Giugno 2021 ", "") + cv[60]) }

        H3 { Text(cv[61]
            .replace(" Crediti formativi presso il dipartimento d’informatica de La Sapienza di Roma.", "")) }
        P { Text(cv[61]
            .replace("2003-2005 ", "")) }

        H3 { Text(cv[62]
            .replace(" Maturità scientifica presso l'istituto Isacco Newton di Roma.", "")) }
        P { Text(cv[62]
            .replace("2003 ", "")) }
    }
}

@Composable
fun comp(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H2 { Text(cv[63]) }

    switchButton(switch)

    if(switch.value==1) {

        P { Text(cv[64] + " " + cv[65]) }
        P { Text(cv[66] + " " + cv[67] + " " + cv[68]) }
        P { Text(cv[69]) }
        P { Text(cv[70]) }
        P { Text(cv[71] + " " + cv[72]) }

        //

        P( { style { paddingLeft(42.px) } }) {

            P { Text(cv[73]) }
            P { Text(cv[74]) }
            P { Text(cv[75]) }
            P { Text(cv[76] + " " + cv[77]) }
            P { Text(cv[78]) }
        }

        //

        P { Text(cv[79]) }
    }
}
