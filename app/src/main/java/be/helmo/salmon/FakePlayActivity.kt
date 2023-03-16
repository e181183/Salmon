package be.helmo.salmon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import be.helmo.salmon.databinding.ActivityPlayBinding
import be.helmo.salmon.model.Game
import be.helmo.salmon.viewModel.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FakePlayActivity: AppCompatActivity()  {

        private lateinit var binding: ActivityPlayBinding

        private  val inputsToFollow = mutableListOf<Int>()
        private var nbInput = 0


    private var nbErreurs = 0

        private lateinit var gameViewModel: GameViewModel

        private  var game  =  Game(26,3650,1)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityPlayBinding.inflate(layoutInflater)
            setContentView(binding.root)

            gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

            binding.backToMenu.setOnClickListener() {
                saveGame(game.getNiveau(),game.getSc(),game.getVies(),game.getSeq())
            }

            binding.redButton.setOnClickListener() {

                verifyInput(0)
            }

            binding.greenButton.setOnClickListener() {

                verifyInput(1)
            }

            binding.blueButton.setOnClickListener() {

                verifyInput(2)
            }

            binding.yellowButton.setOnClickListener() {

                verifyInput(3)
            }
            game.setSeq("0031")
        }





        private fun verifyInput(input: Int) {
            if(input == inputsToFollow[nbInput]){
                nbInput++
                if(nbInput == inputsToFollow.size) {
                    updateScore();
                    game.upgradeLevel()
                    nbInput =0;
                }

            } else {
                game.downgradeLives()
                if(game.getVies() > 0) {
                    nbErreurs++

                } else {
                    GlobalScope.launch(Dispatchers.IO){
                        gameViewModel.deleteFinishedSavedGame()
                    }
                    val intent = Intent(this, GameoverActivity::class.java)
                    intent.putExtra("SCORE", game.getSc())
                    intent.putExtra("NIVEAU", game.getNiveau())

                    startActivity(intent)
                }

            }

            updateVisualElements()
        }

        private fun updateVisualElements() {
            val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
            var highScore = sharedPreferences.getInt("high_score_key", 0)
            if(highScore < game.getSc()) {
                highScore = game.getSc()
                val editor = sharedPreferences.edit()
                editor.putInt("high_score_key", highScore)
                editor.apply()
            }

            binding.niveauActuel.text = getString(R.string.niveau) + game.getNiveau().toString()
            binding.nbVies.text = getString(R.string.vies) + game.getVies().toString()
            binding.actualScore.text = getString(R.string.actual_score) + game.getSc().toString()
            binding.highScore.text =  getString(R.string.highscore) + highScore.toString()
        }

        private fun updateScore() {
            game.upgradeScore(gameViewModel.updateScore(game.getNiveau(), nbErreurs))
            nbErreurs = 0
        }


        private fun saveGame(level : Int, score : Int, lives : Int, sequence : String) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Save game and quit ?")
            builder.setItems(arrayOf("yes", "no")) { _, which ->
                when (which) {
                    0 -> {
                        // Sauve la partie et retourne au menu
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                    1 -> {
                        // Annuler
                    }
                }
            }
            builder.show()
        }





    }
