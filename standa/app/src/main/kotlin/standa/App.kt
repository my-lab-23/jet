package standa

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import standa.Aux.getActiveLink
import standa.Aux.getActiveName
import standa.Aux.getCodeData
import standa.Aux.getInactiveData
import standa.Aux.getNextCodeLink
import standa.Aux.getPageName
import standa.Page.generatePage
import java.io.File

fun main() {

    generatePage(0); auxiliary()
    generatePage(1); auxiliary()
    generatePage(2); auxiliary()
}

fun auxiliary() {

    val hbsPath = "ws/hbs/index.hbs"
    val inactiveData = getInactiveData()
    val combinedLinks = inactiveData.map { Pair(it.link, it.name) }
    val codeData = generateCodeDataMap(getCodeData())
    val nextCodeLink = getNextCodeLink()

    val dataMap = mapOf(
        "activeLink" to getActiveLink(),
        "activeName" to getActiveName(),
        "combinedLinks" to combinedLinks,
        "codeData" to codeData,
        "nextCodeLink" to nextCodeLink
    )

    val htmlContent = createHTMLContent(hbsPath, dataMap)
    val htmlPath = "/home/ema/sesto/standa/ws/${getPageName()}"
    File(htmlPath).writeText(htmlContent)
}

fun createHTMLContent(path: String, data: Map<String, Any>): String {

    val handlebars = Handlebars()
    val hbsContent = File(path).readText()
    val template: Template = handlebars.compileInline(hbsContent)
    return template.apply(data)
}

fun generateCodeDataMap(codeDataList: List<CodeData>): List<Map<String, Any>> {

    return codeDataList.map { codeData ->
        mapOf(
            "codeText" to codeData.codeText,
            "codeLang" to codeData.codeLang,
            "codeId" to codeData.codeId,
            "codeLink" to codeData.codeLink,
        )
    }
}
