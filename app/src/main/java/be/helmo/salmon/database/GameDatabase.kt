package be.helmo.salmon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.helmo.salmon.Game
import be.helmo.salmon.database.dao.GameDao

@Database(entities = [ Game::class ], version=1)
@TypeConverters(GameTypeConverters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun GameDao(): GameDao
}