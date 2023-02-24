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
fun myTable() {

    val style = AppStylesheet

    Div(attrs = { classes(style.column) }) {

        Div(attrs = { classes(style.row, style.pari) }) {

            P { Text("Curriculum vitae di") }
            H1 { Text("Emanuele Quattrini") }
        }

        Div(attrs = { classes(style.row, style.dispari) }) {

            H2 { Text("1. Dati anagrafici") }
            P { Text("Data di nascita: 23/02/1984 a Roma") }
            P { Text("Residenza: Via delle Pleiadi, 1 - 00055 - Ladispoli") }
            P { Text("Nazionalità: Italiana") }
        }

        Div(attrs = { classes(style.row, style.pari) }) {

            H2 { Text("2. Contatti") }
            P { Text("E-mail: emanuelequattrini@proton.me") }
        }

        Div(attrs = { classes(style.row, style.dispari) }) {

            H2 { Text("3. Posizione aspirata") }
            P { Text("Sviluppatore Kotlin – Scala – Java – Python " +
                    "e/o sistemista Linux (o qualsiasi posizione " +
                    "che richieda competenze in ambito informatico).") }
        }

        Div(attrs = { classes(style.row, style.pari) }) {

            H2 { Text("4. Esperienze lavorative") }
            esp0()
            esp1()
            esp2()
            esp3()
            esp4()
        }

        Div(attrs = { classes(style.row, style.dispari) }) { istruzione() }

        Div(attrs = { classes(style.row, style.pari) }) { comp() }

        Div(attrs = { classes(style.row, style.dispari) }) {

            H2 { Text("7. Interessi") }
            P { Text("Musica classica, fotografia, scacchi, lettura (saggi e fantascienza).") }
        }

    }
}

@Composable
fun switchButton(switch: MutableState<Int>) {

    Button(attrs = {
        onClick {

            switch.value = if(switch.value==0) 1 else 0
        } }) {

        if(switch.value==0) Text("Mostra")
        else Text("Nascondi")
    }
}

@Composable
fun istruzione() {

    val switch = remember { mutableStateOf(0) }

    H2 { Text("5. Istruzione") }

    switchButton(switch)

    if(switch.value==1) {

        H3 { Text("Aprile-Giugno 2021") }
        P { Text("Attestato Teoria delle Reti, Windows Server, Linux Server, Oracle " +
                "presso l’IstitutoCEFI di Roma.") }
        H3 { Text("2003-2005") }
        P { Text("Crediti formativi presso il dipartimento d’informatica de La Sapienza di Roma.") }
        H3 { Text("2003") }
        P { Text("Maturità scientifica presso l'istituto Isacco Newton di Roma.") }
    }
}

@Composable
fun comp() {

    val switch = remember { mutableStateOf(0) }

    H2 { Text("6. Competenze") }

    switchButton(switch)

    if(switch.value==1) {

        P { Text("Buona conoscenza di Microsoft Windows e GNU/Linux come postazione di lavoro e dei " +
                "principali applicativi (es. posta elettronica, suite ufficio).") }
        P { Text("Buona conoscenza dei principi della programmazione e dello shell scripting, in particolare " +
                "dei linguaggi Kotlin, Scala, Java, Python, Ruby e C (Arduino).") }
        P { Text("Buona conoscenza delle piattaforme cloud (es. AWS, Azure).") }
        P { Text("Buona conoscenza dei principi dell'amministrazione di rete (TCP/IP, IPv4, IPv6, DHCP, NAT, " +
                "SSH, VPN, NFS, DNS, reverse proxy) e di GNU/Linux e dei principali applicativi in ambito " +
                "server:") }

        //

        P( { style { paddingLeft(42.px) } }) {

            P { Text("- database PostgreSQL (jsonb), Oracle, MySQL, Microsoft SQL Server, MariaDB; ") }
            P { Text("- monitoraggio Nagios (aggiunta hosts, plugin, NRPE, check di base, notifiche); ") }
            P { Text("- containerizzazione Docker; ") }
            P { Text("- virtualizzazione QEMU/KVM con Proxmox, Oracle VirtualBox con Vagrant, Vmware (workstation); ") }
            P { Text("- orchestrazione Ansible, Puppet Bolt.") }
        }

        //

        P { Text("Lingue: Italiano ed inglese tecnico informatico.") }
    }
}
