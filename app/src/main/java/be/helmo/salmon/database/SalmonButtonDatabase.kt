package be.helmo.salmon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.helmo.salmon.SalmonButton
import be.helmo.salmon.database.dao.SalmonButtonDao

@Database(entities = [ SalmonButton::class ], version=1)
abstract class SalmonButtonDatabase : RoomDatabase() {
    abstract fun SalmonButtonDao(): SalmonButtonDao

    companion object {
        private const val DATABASE_NAME = "salmon_button_database"
        //@Volatile
        private var instance: SalmonButtonDatabase? = null

        fun initDatabase(context: Context){
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    SalmonButtonDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
        }

        fun getInstance(): SalmonButtonDatabase? {
            checkNotNull(instance) { "Game database must be initialized" }
            return instance
        }

        fun disconnectDatabase(){
            instance = null
        }
    }
}