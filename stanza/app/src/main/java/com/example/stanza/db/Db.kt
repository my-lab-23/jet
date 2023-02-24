package com.example.stanza.db

import android.content.Context
import androidx.room.*

@Entity
data class ContactDB(val nome: String, val cognome: String, val telefono: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface ContactDao {

    @Insert
    fun insert(x: ContactDB): Long

    @Query("Delete from ContactDB " +
            "where nome = :nome " +
            "and cognome = :cognome " +
            "and telefono = :telefono")
    fun delete(nome: String, cognome: String, telefono: String)

    @Query("select * from ContactDB")
    fun loadAll(): List<ContactDB>
}

@Database(version = 1, entities = [ContactDB::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app_database")
                .allowMainThreadQueries()
                .build().apply {
                    instance = this
                }
        }
    }
}
