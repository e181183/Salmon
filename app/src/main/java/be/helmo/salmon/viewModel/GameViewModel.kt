package be.helmo.salmon.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import be.helmo.salmon.database.GameDatabase
import be.helmo.salmon.model.Game
import be.helmo.salmon.database.repository.GameRepository

class GameViewModel (application: Application) : AndroidViewModel(application)  {

    private val repository : GameRepository

    init {
        val gameDao = GameDatabase.getDatabase(application).GameDao()
        repository = GameRepository(gameDao)
    }
    fun SaveGame(game : Game) {
        repository.addSavedGame(game)
    }

    fun DeleteFinishedSavedGame() {
        repository.deleteSavedGame()
    }

    fun getGame(): LiveData<Game> {
        return repository.getGame()
    }

    fun getLevel(): Int {
        return repository.getLevel()
    }

    fun getScore(): Int {
        return repository.getScore()
    }

    fun getLifes(): Int {
        return repository.getLifes()
    }

    fun getSequence(): String {
        return repository.getSequence()
    }

    fun getCountGame() : Int {
        return repository.getCountGame()
    }
}