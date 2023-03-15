package be.helmo.salmon

import androidx.lifecycle.ViewModelProvider
import org.junit.Test

import be.helmo.salmon.model.Game
import be.helmo.salmon.viewModel.GameViewModel
import org.junit.Assert.*


class GameViewModelTest {

    private var  playActivity : PlayActivity = PlayActivity()
    private  var gameViewModel = ViewModelProvider(playActivity).get(GameViewModel::class.java)
    @Test
    fun saveGame_countGame() {
        assertEquals(gameViewModel.getCountGame(), 0)
        gameViewModel.SaveGame(Game(1, 0, 0, 3, "sequence"))
        assertEquals(gameViewModel.getCountGame(), 1)

    }
}