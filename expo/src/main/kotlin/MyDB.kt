import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

fun connectDB() {

    Database.connect("jdbc:postgresql://localhost:5432/expo", driver = "org.postgresql.Driver",
        user = "expo", password = "expo")
}

fun resetTable() {

    SchemaUtils.drop(TriviaTable)
    SchemaUtils.create(TriviaTable)
}

fun createRow(i: Int, j: Int) {

    val questionLocal = loadQuestion(i, j)
    val correctLocal = loadCorrect(i, j)
    val incorrectLocal = loadIncorrect(i, j)

    val id = TriviaTable.insertAndGetId {
        it[question] = questionLocal
        it[correct] = correctLocal
        it[incorrect1] = incorrectLocal[0]
        it[incorrect2] = incorrectLocal[1]
        it[incorrect3] = incorrectLocal[2]
    }

    println(id.value)
}

object TriviaTable: IntIdTable() {

    val question = varchar("question", 1000)
    val correct = varchar("correct", 100)
    val incorrect1 = varchar("incorrect1", 100)
    val incorrect2 = varchar("incorrect2", 100)
    val incorrect3 = varchar("incorrect3", 100)
}
