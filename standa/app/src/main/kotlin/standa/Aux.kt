package standa

import standa.Page.NAME0
import standa.Page.NAME1
import standa.Page.NAME2

data class LinkData(val link: String, val name: String)

data class CodeData(

    val codeText: String,
    val codeLang: String,
    val codeId: String,
    val codeLink: String,
)

//

open class Shared {

    protected var url = ""
    protected var active = LinkData("", "")
    protected var inactive = listOf<LinkData>()
    protected var codeDetails = listOf<CodeData>()
    protected var nextLink = ""

    protected val zero = listOf("index.html", NAME0)
    protected val uno = listOf(NAME1.lowercase() + ".html", NAME1)
    protected val due = listOf(NAME2.lowercase() + ".html", NAME2)
}

//

object Aux: Shared() {

    fun getPageName(): String = url
    fun getActiveLink(): String = active.link
    fun getActiveName(): String = active.name
    fun getInactiveData(): List<LinkData> = inactive
    fun getCodeData(): List<CodeData> = codeDetails
    fun getNextCodeLink(): String = nextLink
}
