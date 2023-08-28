package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.dom.*

@Composable
fun esp0(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H3 { Text(cv[16]) }

    switchButton(switch)

    if(switch.value==1) {

        P { Text(cv[17]) }
        H4 { Text(cv[18]) }
        A(attrs = { href(cv[19]) }) { Text(cv[19]) }
        P { Text(cv[20]) }
        A (attrs = { href(cv[21]) }) { Text(cv[21]) }
        H4 { Text(cv[22]) }
        P { Text(cv[23]) }
        P { Text(cv[24]) }
        P { Text(cv[25]) }
        P { Text(cv[26] + " " + cv[27]) }
    }
}

@Composable
fun esp1(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H3 { Text(cv[28]) }

    switchButton(switch)

    if(switch.value==1) {

        P { Text(cv[29]) }
        P { Text(cv[30] + " " + " " + cv[31] + " " + cv[32] + " " + cv[33]
            .replace(" https://github.com/my-lab-23", "")) }

        val url = cv[33].replace("visualizzazione]. ", "")

        A(attrs = { href(url) }) { Text(url) }

        H4 { Text(cv[34]) }
        P { Text(cv[35]) }
        P { Text(cv[36] + " " + cv[37]) }
        P { Text(cv[38]) }
        P { Text(cv[39]) }
    }
}

@Composable
fun esp2(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H3 { Text(cv[40]) }

    switchButton(switch)

    if(switch.value==1) {

        P { Text(cv[41] + " " + cv[42]) }
        P { Text(cv[43] + " " + cv[44] + " " + cv[45]) }
        P { Text(cv[46] + " " + cv[47]) }
    }
}

@Composable
fun esp3(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H3 { Text(cv[48]) }

    switchButton(switch)

    if(switch.value==1) {

        P { Text(cv[49]) }
        P { Text(cv[50] + " " + cv[51]) }
        P { Text(cv[52]) }
        P { Text(cv[53]) }
    }
}

@Composable
fun esp4(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H3 { Text(cv[54]) }

    switchButton(switch)

    if(switch.value==1) {

        P { Text(cv[55] + " " + cv[56]
            .replace(" https://github.com/my-lab-23", "")) }

        val url = cv[56].replace("di un progetto universitario. ", "")

        A(attrs = { href(url) }) { Text(url) }

        P { Text(cv[57]) }
    }
}
