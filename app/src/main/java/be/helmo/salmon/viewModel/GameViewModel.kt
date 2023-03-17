package be.helmo.salmon.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import be.helmo.salmon.database.GameDatabase
import be.helmo.salmon.model.Game
import be.helmo.salmon.database.repository.GameRepository

class GameViewModel(application: Application) : AndroidViewModel(application)  {

     private val repository : GameRepository

    init {
        val gameDao = GameDatabase.getDatabase(application).GameDao()
        repository = GameRepository(gameDao)
    }
    fun saveGame(game: Game) {
        repository.addSavedGame(game)
    }

    fun deleteFinishedSavedGame() {
        repository.deleteSavedGame()
    }

    fun getLevel(): Int {
        return repository.getLevel()
    }

    fun getScore(): Int {
        return repository.getScore()
    }

    fun getLives(): Int {
        return repository.getLives()
    }

    fun getSequence(): String {
        return repository.getSequence()
    }

    fun getCountGame() : Int {
        return repository.getCountGame()
    }

    fun updateScore(niveau: Int, nbErreurs: Int): Int {
        return ((6 * niveau) - (nbErreurs * 3))
    }

    fun pickAnInput() : Int {
        return (0..3).random()
    }

}