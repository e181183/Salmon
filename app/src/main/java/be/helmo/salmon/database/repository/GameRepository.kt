package be.helmo.salmon.database.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import be.helmo.salmon.model.Game
import be.helmo.salmon.database.GameDatabase
import be.helmo.salmon.database.dao.GameDao

class GameRepository(private val gameDao: GameDao) {

    fun addSavedGame(game : Game) {
        gameDao.addSavedGame(game)
    }

    fun deleteSavedGame(game : Game){
        gameDao.deleteSavedGame(game)
    }

    fun getGame() : LiveData<Game> {
        return gameDao.getGame()
    }

    fun getLevel(): Int {
        return gameDao.getLevel()
    }

    fun getScore(): Int {
        return gameDao.getScore()
    }

    fun getLifes(): Int {
        return gameDao.getLifes()
    }

    fun getSequence(): String {
        return gameDao.getSequence()
    }

    fun getCountGame() : Int {
        return gameDao.getCountGame()
    }
}