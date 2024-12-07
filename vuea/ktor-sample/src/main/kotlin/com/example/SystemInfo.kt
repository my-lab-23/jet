package com.example

import java.io.BufferedReader
import java.io.InputStreamReader

fun getSystemInfo(): Map<String, String> {
    val systemInfo = mutableMapOf<String, String>()

    // Recupera le informazioni sul sistema operativo
    val os = runCommand("lsb_release -d").trim()
    systemInfo["os"] = os.replace("Description:", "")

    // Recupera il modello della CPU
    // val cpu = runCommand("lscpu | grep 'Model name'").trim()
    // systemInfo["cpu"] = cpu

    // Recupera le informazioni sulla memoria
    // val memory = runCommand("free -h | grep Mem").trim()
    // systemInfo["memory"] = memory

    // Recupera l'utilizzo del disco
    // val disk = runCommand("df -h | grep /dev/").trim()
    // systemInfo["disk"] = disk

    // Recupera l'indirizzo IP della rete
    val network = runCommand("hostname -I").trim()
    systemInfo["network"] = network

    return systemInfo
}

// Funzione per eseguire un comando nel sistema
fun runCommand(command: String): String {
    return try {
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        reader.readText()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
