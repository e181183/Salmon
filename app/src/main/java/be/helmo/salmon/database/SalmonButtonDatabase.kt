package be.helmo.salmon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.helmo.salmon.SalmonButton
import be.helmo.salmon.database.dao.SalmonButtonDao

@Database(entities = [ SalmonButton::class ], version=1)
@TypeConverters(GameTypeConverters::class)
abstract class SalmonButtonDatabase : RoomDatabase() {
    abstract fun SalmonButtonDao(): SalmonButtonDao
}