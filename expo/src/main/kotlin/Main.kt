import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.system.measureTimeMillis

suspend fun main() {

    repeat(10) {

        loadJsonFile()
    }

    connectDB()

    transaction {

        addLogger(StdOutSqlLogger)

        resetTable()

        val elapsed = measureTimeMillis {
            for (i in 0..49) {
                for (j in 0..9) {
                    createRow(i, j)
                }
            }

        }

        println(elapsed)

        //

        val sql = buildString {
            append("DELETE FROM trivia a USING trivia b ")
            append("WHERE a.id < b.id AND a.question = b.question;")
        }

        exec(sql)

        //
    }
}
