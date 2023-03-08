package be.helmo.salmon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.dao.SalmonButtonDao

@Database(entities = [ SalmonButton::class ], version=1)
@TypeConverters(SalmonButtonTypeConverters::class)
abstract class SalmonButtonDatabase : RoomDatabase() {
    abstract fun SalmonButtonDao(): SalmonButtonDao

    companion object {
        private const val DATABASE_NAME = "salmon_button_database"
        @Volatile
        private var INSTANCE: SalmonButtonDatabase? = null

        fun getDatabase(context: Context) : SalmonButtonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SalmonButtonDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun disconnectDatabase(){
            INSTANCE = null
        }
    }
}