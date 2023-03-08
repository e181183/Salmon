package be.helmo.salmon.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.helmo.salmon.model.Game
import be.helmo.salmon.database.repository.GameRepository

class GameViewModel : ViewModel()  {

    fun SaveGame(game : Game) {
        GameRepository.getInstance()!!.addSavedGame(game)
    }

    fun DeleteFinishedSavedGame(game : Game) {
        GameRepository.getInstance()!!.deleteSavedGame(game)
    }

    fun getGame(): LiveData<Game> {
        return GameRepository.getInstance()!!.getGame()
    }
}