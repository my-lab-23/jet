package com.example.scrivi

import android.content.Context
import androidx.room.*

@Entity
data class MsgDB(val msg: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface MsgDao {

    @Insert
    fun insert(x: MsgDB): Long

    @Query("select * from MsgDB")
    fun loadAll(): List<MsgDB>

    // Cancellazione di tutti i messaggi
    @Query("delete from MsgDB")
    fun deleteAll()
}

@Database(version = 1, entities = [MsgDB::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun msgDao(): MsgDao
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
