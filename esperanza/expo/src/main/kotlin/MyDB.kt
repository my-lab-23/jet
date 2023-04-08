import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import java.io.File
import java.text.SimpleDateFormat

object MyDB {

    fun connectDB() {

        Database.connect(
            "jdbc:postgresql://localhost:5432/expo", driver = "org.postgresql.Driver",
            user = "expo", password = "expo"
        )
    }

    fun resetTable() {

        SchemaUtils.drop(MsgTable)
        SchemaUtils.create(MsgTable)
    }

    fun createRow(msg: String, timeStamp: Long) {

        try {

            MsgTable.insert {
                it[MsgTable.msg] = msg
                it[MsgTable.timeStamp] = timeStamp
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    object MsgTable : IntIdTable() {

        val msg = text("msg")
        val timeStamp = long("timeStamp")
    }

    fun searchByTimeStamp(timeStamp: Long) {
        val result = MsgTable.select { MsgTable.timeStamp eq timeStamp }
        result.forEach { row ->

            val msg = row[MsgTable.msg]
            println(msg)
        }
    }

    fun searchLastNMsg(n: Int) {

        val results = MsgTable.selectAll()
            .orderBy(MsgTable.timeStamp, SortOrder.DESC)
            .limit(n)
            .toList()

        val lock = File("/home/ktor/data/lock")
        File("/home/ktor/data/lastNMsg.txt").writeText("")

        results.reversed().forEach { row ->
            val msg = row[MsgTable.msg]
            println(msg)
            val result = MySerialization
                .serialize(Msg(row[MsgTable.msg], row[MsgTable.timeStamp]))
            File("/home/ktor/data/lastNMsg.txt").appendText("$result\n")
        }

        deleteFile(lock)
    }

    private fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
            println("\nLock file deleted\n")
        }
    }

    fun searchBetweenDate(date1: String, date2: String) {

        val startDate: Long = getTimeStamp(date1)
        val endDate: Long = getTimeStamp(date2)

        println(startDate)
        println(endDate)

        val results = MsgTable.select {

            (MsgTable.timeStamp greaterEq startDate) and (MsgTable.timeStamp lessEq endDate)

        }.orderBy(MsgTable.timeStamp, SortOrder.DESC).toList()

        results.reversed().forEach { row ->
            val msg = row[MsgTable.msg]
            println(msg)
        }
    }

    private fun getTimeStamp(dateString: String): Long {

        val format = SimpleDateFormat("dd-MM-yyyy")
        val date = format.parse(dateString)
        return date.time
    }
}
