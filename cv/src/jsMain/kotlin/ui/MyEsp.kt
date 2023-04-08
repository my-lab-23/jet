package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.dom.*

@Composable
fun esp0(cv: List<String>) {

    val switch = remember { mutableStateOf(0) }

    H3 { Text("Da settembre 2022 (progetti personali)") }

    switchButton(switch)

    if(switch.value==1) {

        P { Text("Progettazione di applicazioni client-server mobili e desktop.") }
        H4 { Text("App dimostrativa sul Google Play Store:") }

        A(attrs = { href("https://play.google.com/store/apps/details?id=it.emanueleweb.solaria") }) {
            Text("https://play.google.com/store/apps/details?id=it.emanueleweb.solaria") }

        P { Text("Codice sorgente (nella root del repository ci sono altri esempi):") }

        A (attrs = { href("https://github.com/my-lab-23/jet/tree/master/solaria") }) {
            Text("https://github.com/my-lab-23/jet/tree/master/solaria") }

        H4 { Text("Tecnologie utilizzate:") }
        P { Text("Kotlin (Android SDK, Compose Android e Desktop, Ktor, Coroutines).") }
        P { Text("Data persistence (Firebase, Room, Exposed)") }
        P { Text("Build tool Gradle, IDE (IntelliJ IDEA, Android Studio), GitHub.") }
        P { Text(cv[26] + " " + cv[27]) }
    }
}

@Composable
fun esp1 () {

    val switch = remember { mutableStateOf(0) }

    H3 { Text("Settembre 2021 – Settembre 2022 (progetti personali)") }

    switchButton(switch)

    if(switch.value==1) {

        P { Text("Preparazione dei dati per l’apprendimento automatico.") }
        P { Text("[Per esempio sto scrivendo un programma in Scala che intercetta in tempo reale i tweet con " +
                "un determinato hashtag, li trasforma tramite tecniche di NLP (es. tokenizzazione," +
                "rimozione stop words, conteggio frequenza parole etc.) e carica i dati su un sito web per la" +
                "visualizzazione].") }

        A(attrs = { href("https://github.com/my-lab-23") }) {
            Text("https://github.com/my-lab-23") }

        H4 { Text("Tecnologie utilizzate:") }
        P { Text("Build tool (Gradle, Sbt), package manager Conda, IDE (IntelliJ IDEA, PyCharm), GitHub.") }
        P { Text("Lato server –> Scala (Spark, MLlib, Akka, twitter4s, doobie), Python (NumPy, Scikit-Learn, " +
                "Pandas, Matplotlib, Psycopg2, PySpark).") }
        P { Text("Framework Play, Rasa, Selenium, Auth0, testing JUnit, ScalaTest.") }
        P { Text("Lato client –> Kotlin (Android SDK, Ktor, Compose).") }
    }
}

@Composable
fun esp2 () {

    val switch = remember { mutableStateOf(0) }

    H3 { Text("Settembre 2020 – Settembre 2021 (progetti personali)") }

    switchButton(switch)

    if(switch.value==1) {

        P { Text("Progettazione e amministrazione di sistemi per l'automazione delle infrastrutture " +
                "informatiche.") }
        P { Text("Tecnologie utilizzate: Amazon AWS (VPC per la creazione di reti virtuali sicure, istanze EC2 " +
                "basate su Red Hat Enterprise Linux e Debian GNU/Linux, IAM e Cognito per la gestione " +
                "della sicurezza degli account), containerizzazione Docker, virtualizzazione QEMU/KVM.") }
        P { Text("Monitoraggio Nagios (aggiunta hosts, plugin, NRPE, check di base, notifiche), " +
                "orchestrazione Puppet Bolt per l’invio in parallelo di comandi a gruppi di istanze.") }
    }
}

@Composable
fun esp3 () {

    val switch = remember { mutableStateOf(0) }

    H3 { Text("Gennaio 2005 – Settembre 2020 presso Agricola Quattrini") }

    switchButton(switch)

    if(switch.value==1) {

        P { Text("Progettazione e amministrazione della rete locale dell'azienda.") }
        P { Text("Tecnologie utilizzate: Debian GNU/Linux, NFS per la condivisione dei file, OpenOffice per la " +
                "creazione dei documenti, CUPS per la condivisione delle stampanti di rete.") }
        P { Text("Progettazione e amministrazione del sito web aziendale (Gennaio 2008 – Dicembre 2012).") }
        P { Text("Tecnologie utilizzate: Debian GNU/Linux, Apache , Ruby on Rails, PostgreSQL.") }
    }
}

@Composable
fun esp4 () {

    val switch = remember { mutableStateOf(0) }

    H3 { Text("Luglio 2005 – Settembre 2005") }

    switchButton(switch)

    if(switch.value==1) {

        P { Text("Progettazione di una piattaforma per la gestione di un all'allevamento di bovini nell'ambito " +
                "di un progetto universitario.") }

        A(attrs = { href("https://github.com/my-lab-23") }) {
            Text("https://github.com/my-lab-23") }

        P { Text("Tecnologie utilizzate: Slackware GNU/Linux, Ruby on Rails versione 0.12.1, PostgreSQL.") }
    }
}
