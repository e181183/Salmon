package be.helmo.salmon.database.repository

import be.helmo.salmon.model.Game
import be.helmo.salmon.database.dao.GameDao

class GameRepository(private val gameDao: GameDao) {

    fun addSavedGame(game : Game) {
        gameDao.addSavedGame(game)
    }

    fun deleteSavedGame() {
        gameDao.deleteSavedGame()
    }

    fun getLevel(): Int {
        return gameDao.getLevel()
    }

    fun getScore(): Int {
        return gameDao.getScore()
    }

    fun getLives(): Int {
        return gameDao.getLives()
    }

    fun getSequence(): String {
        return gameDao.getSequence()
    }

    fun getCountGame() : Int {
        return gameDao.getCountGame()
    }
}