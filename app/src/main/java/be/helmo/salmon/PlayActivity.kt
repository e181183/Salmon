package be.helmo.salmon

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import be.helmo.salmon.databinding.ActivityMainBinding
import be.helmo.salmon.databinding.ActivityPlayBinding
import be.helmo.salmon.model.Game
import be.helmo.salmon.viewModel.GameViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding

    private  val inputsToFollow = mutableListOf<Int>()
    private var niveau = 1;
    private var nbInput = 0;
    private var vies = 3;
    private var score = 0;
    private var nbErreurs = 0;
    private var sequence = ""

    private lateinit var gameViewModel: GameViewModel

    override fun onBackPressed() {
        
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isNew = intent.getBooleanExtra("isNew", true)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        GlobalScope.launch {
            if(gameViewModel.getCountGame() != 0 && !isNew) {
                sequence = gameViewModel.getSequence()
                for (i in 0 until sequence.length)
                inputsToFollow.add(gameViewModel.getSequence().get(i).toString().toInt())
            }
        }

        binding.backToMenu.setOnClickListener() {
            saveGame(niveau, score, sequence)
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

        updateVisualElements()

        Timer().schedule(1000) {playGame()}
    }

    private fun playGame() {
        var input = pickAnInput()
        inputsToFollow.add(input)
        sequence += input.toString()
        disableEnableClick()
        displayInput(inputsToFollow, 0)

    }

    private fun displayInput(inputList: List<Int>, index: Int) {


        if (index >= inputList.size) {
            disableEnableClick()
            return // stop recursion when all images have been displayed
        }

        val input = inputList[index]
        val button = when(input) {
            0 -> binding.redButton
            1 -> binding.greenButton
            2 -> binding.blueButton
            3 -> binding.yellowButton
            else -> return // handle invalid input
        }

        runOnUiThread { getDisplayResource(input) }
        Timer().schedule(1000) {
            runOnUiThread {
                getBaseButtonResource(input)
                Timer().schedule(500){
                    displayInput(inputList, index + 1)
                }
            }
        }
    }

    private fun getDisplayResource(input: Int) = when(input) {
        0 -> binding.redButton.foreground = getDrawable(R.drawable.salmon_rouge)
        1 -> binding.greenButton.foreground = getDrawable(R.drawable.salmon_vert)
        2 -> binding.blueButton.foreground = getDrawable(R.drawable.salmon_bleu)
        3 -> binding.yellowButton.foreground = getDrawable(R.drawable.salmon_jaune)
        else -> binding.redButton.foreground = getDrawable(R.drawable.salmon_rouge) // handle invalid input
    }

    private fun getBaseButtonResource(input: Int) = when(input) {
        0 -> binding.redButton.foreground = null
        1 -> binding.greenButton.foreground = null
        2 -> binding.blueButton.foreground = null
        3 -> binding.yellowButton.foreground = null
        else -> binding.redButton.foreground = null // handle invalid input
    }

    private fun pickAnInput() : Int {
         return (0..3).random();
    }

    private fun verifyInput(input: Int) {
        if(input == inputsToFollow.get(nbInput)){
            nbInput++;
            if(nbInput == inputsToFollow.size) {
                Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show()
                updateScore();
                niveau++;
                nbInput =0;
                playGame();
            }

        } else {
            vies--;
            if(vies > 0) {
                Toast.makeText(this, R.string.Incorrect, Toast.LENGTH_SHORT).show()
                nbErreurs++;
                disableEnableClick()
                displayInput(inputsToFollow, 0);
            } else {
                val intent = Intent(this, GameoverActivity::class.java)
                intent.putExtra("SCORE", score)
                intent.putExtra("NIVEAU", niveau)

                startActivity(intent)
            }

        }

        updateVisualElements();
    }

    private fun updateVisualElements() {
        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var highScore = sharedPreferences.getInt("high_score_key", 0)
        if(highScore < score) {
            highScore = score
            val editor = sharedPreferences.edit()
            editor.putInt("high_score_key", highScore)
            editor.apply()
        }

        binding.niveauActuel.text = getString(R.string.niveau) + niveau.toString()
        binding.nbVies.text = getString(R.string.vies) + vies.toString()
        binding.actualScore.text = getString(R.string.actual_score) + score.toString()
        binding.highScore.text =  getString(R.string.highscore) + highScore.toString()
    }

    private fun updateScore() {
        score += ((6 * niveau) - (nbErreurs * 3))
        nbErreurs = 0;
    }

    private fun disableEnableClick() {
        runOnUiThread() {
            binding.redButton.isEnabled = !(binding.redButton.isEnabled)
            binding.greenButton.isEnabled = !(binding.greenButton.isEnabled)
            binding.blueButton.isEnabled = !(binding.blueButton.isEnabled)
            binding.yellowButton.isEnabled = !(binding.yellowButton.isEnabled)
        }
    }

    private fun saveGame(level : Int, score : Int, sequence : String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Save game and quit ?")
        builder.setItems(arrayOf("yes", "no")) { _, which ->
            when (which) {
                0 -> {
                    // Sauve la partie et retourne au menu
                    val game = Game(1, level, score, sequence)
                    GlobalScope.launch {
                        gameViewModel.SaveGame(game)
                    }
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