package be.helmo.salmon.database.repository

import androidx.lifecycle.LiveData
import be.helmo.salmon.Game
import be.helmo.salmon.database.GameDatabase
import be.helmo.salmon.database.dao.GameDao

class GameRepository {

    private val gameDao : GameDao = GameDatabase.getInstance()!!.GameDao()

    fun addSavedGame(game : Game) {
        gameDao.addSavedGame(game)
    }

    fun deleteSavedGame(game : Game){
        gameDao.deleteSavedGame(game)
    }

    fun getGame() : LiveData<Game> {
        return gameDao.getGame(1)
    }

    companion object {
        private var instance : GameRepository? = null
        fun getInstance(): GameRepository? {
            if (instance == null) {
                instance = GameRepository()
            }
            return instance
        }
    }
}