package be.helmo.salmon.database.repository

import be.helmo.salmon.Game
import be.helmo.salmon.database.dao.GameDao

class GameRepository(private val gameDao: GameDao) {

    fun addSavedGame(game : Game) {
        gameDao.addSavedGame(game)
    }

    fun replaceSavedGame(){
        gameDao.replaceSavedGame(1)
    }

    fun getSavedGame() : Game{
        return gameDao.getGame(1)
    }
}