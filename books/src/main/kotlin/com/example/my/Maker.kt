package com.example.my

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import java.io.File

object Maker {

    fun makeHTML0(fileName: String, dati: Map<String, Map<String, Int>>): String? {

        val hbsFile = File("./ws/$fileName.hbs")
        val template = aux(hbsFile)
        val datiOrdinati = dati.toList()
            .sortedByDescending { (_, value) -> value["num2"] }.toMap()
        return template.apply(datiOrdinati)
    }

    fun makeHTML1(fileName: String, dati: MutableMap<String, Int>): String? {

        val hbsFile = File("./ws/$fileName.hbs")
        val template = aux(hbsFile)
        val datiOrdinati = dati.toList()
            .sortedByDescending { (_, value) -> value}.toMap()
        return template.apply(datiOrdinati)
    }

    inline fun <reified T> makeHTML2(fileName: String, data: List<T>): String? {
        val hbsFile = File("./ws/$fileName.hbs")
        val template = aux(hbsFile)
        return template.apply(data)
    }

    //

    fun aux(hbsFile: File): Template {

        val hbsContent = hbsFile.readText()
        val handlebars = Handlebars()
        return handlebars.compileInline(hbsContent)
    }

    //

    fun transformData(input: Map<String, Pair<Int, Int>>): Map<String, Map<String, Int>> {
        val output = mutableMapOf<String, Map<String, Int>>()
        for ((key, value) in input) {
            output[key] = mapOf("num1" to value.first, "num2" to value.second)
        }
        return output
    }
}
