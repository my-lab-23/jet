package it.emanueleweb.solaria.db

import android.content.Context
import androidx.room.*

@Entity
data class FavDB(val favID: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface FavDao {

    @Insert
    fun insert(favID: FavDB): Long

    @Query("DELETE FROM FavDB WHERE favID = :favID")
    fun delete(favID: String)

    // Check if a favID is already in the database
    @Query("SELECT * FROM FavDB WHERE favID = :favID")
    fun getFav(favID: String): FavDB?
}

@Database(version = 1, entities = [FavDB::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao(): FavDao
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
