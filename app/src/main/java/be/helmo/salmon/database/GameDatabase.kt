package be.helmo.salmon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import be.helmo.salmon.Game
import be.helmo.salmon.database.dao.GameDao

@Database(entities = [ Game::class ], version=1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun GameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "game_database"
        //@Volatile
        private var instance: GameDatabase? = null

        fun initDatabase(context: Context){
            if(instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
        }

        fun getInstance(): GameDatabase? {
            checkNotNull(instance) { "Game database must be initialized" }
            return instance
        }

        fun disconnectDatabase(){
            instance = null
        }
    }
}