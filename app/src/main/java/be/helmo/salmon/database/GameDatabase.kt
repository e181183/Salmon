package be.helmo.salmon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.helmo.salmon.model.Game
import be.helmo.salmon.database.dao.GameDao

@Database(entities = [ Game::class ], version=1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun GameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "game_database"
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context) : GameDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
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