package pdf

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

fun main() { readPDF() }

fun readPDF() {

    val document = PDDocument.load(File("./cv.pdf"))
    val stripper = PDFTextStripper()
    val text = stripper.getText(document)

    toTextFile(text)
    toJson(text)

    document.close()
}

fun toTextFile(text: String) {

    File("./cv.txt").writeText(text)
}

@Serializable
data class CV(val txt: String)

fun toJson(text: String) {

    val cv = CV(text)
    val json = Json.encodeToString(cv)
    File("./cv.json").writeText(json)
}
