package be.helmo.salmon

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import be.helmo.salmon.databinding.ActivityPlayBinding
import be.helmo.salmon.model.Game
import be.helmo.salmon.viewModel.GameViewModel
import be.helmo.salmon.viewModel.SalmonButtonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.concurrent.schedule


class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding

    private  val inputsToFollow = mutableListOf<Int>()
    private var nbInput = 0
    private var nbErreurs = 0
    private var isNew : Boolean = true

    private lateinit var gameViewModel: GameViewModel

    private lateinit var buttonViewmodel : SalmonButtonViewModel

    private lateinit var sound : Sound

    private var bitmaps = mutableListOf<Bitmap>()

    private lateinit var game: Game

    override fun onBackPressed() {
        saveGame(game.getNiveau(),game.getSc(),game.getVies(),game.getSeq())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isNew = intent.getBooleanExtra("isNew", true)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        buttonViewmodel = ViewModelProvider(this)[SalmonButtonViewModel::class.java]

        sound = Sound(this, buttonViewmodel)

        bitmaps = mutableListOf<Bitmap>(
            BitmapFactory.decodeResource(resources, R.drawable.bouton_base_rouge),
            BitmapFactory.decodeResource(resources, R.drawable.bouton_base_vert),
            BitmapFactory.decodeResource(resources, R.drawable.bouton_base_bleu),
            BitmapFactory.decodeResource(resources, R.drawable.bouton_base_jaune)
        )

        lifecycleScope.launch(Dispatchers.IO) {
            if(gameViewModel.getCountGame() != 0 && !isNew) {
                initValues()
            } else {
                game = Game(1,0,3)
            }
            updateVisualElements()

            buttonViewmodel.getImagesFromDb(bitmaps)
            withContext(Dispatchers.Main) {
                initButtons()
            }
        }

        binding.saveAndQuit.setOnClickListener() {
            saveGame(game.getNiveau(),game.getSc(),game.getVies(),game.getSeq())
        }

        buttonClickListener(binding.redButton, 0)

        buttonClickListener(binding.greenButton, 1)

        buttonClickListener(binding.blueButton, 2)

        buttonClickListener(binding.yellowButton, 3)

        Timer().schedule(1000) {playGame(isNew)}
    }

    private fun buttonClickListener(button : View, input: Int) {
        button.setOnClickListener() {
            runOnUiThread { getDisplayResource(input) }
            verifyInput(input)
            sound.playAudio(input + 1)
            Timer().schedule(400) {
                runOnUiThread { getBaseButtonResource(input)}
            }
        }
    }

    private fun playGame(addSequence : Boolean) {
        if (addSequence) {
            var input = gameViewModel.pickAnInput()
            inputsToFollow.add(input)
            game.setSeq(game.getSeq() + input.toString())
        }
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

        sound.playAudio(input+1)
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

    private fun verifyInput(input: Int) {
        if(input == inputsToFollow[nbInput]){
            nbInput++
            if(nbInput == inputsToFollow.size) {
                Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show()
                updateScore()
                game.upgradeLevel()
                nbInput =0
                Timer().schedule(1500){playGame(true);}
            }

        } else {
            game.downgradeLives()
            if(game.getVies() > 0) {
                Toast.makeText(this, R.string.Incorrect, Toast.LENGTH_SHORT).show()
                nbErreurs++
                nbInput =0
                disableEnableClick()
                Timer().schedule(1500){ displayInput(inputsToFollow, 0);}

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
        binding.highscore.text =  getString(R.string.highscore) + highScore.toString()
    }

    private fun updateScore() {
        game.upgradeScore(gameViewModel.updateScore(game.getNiveau(), nbErreurs))
        nbErreurs = 0
    }

    private fun disableEnableClick() {
        runOnUiThread() {
            binding.redButton.isEnabled = !(binding.redButton.isEnabled)
            binding.greenButton.isEnabled = !(binding.greenButton.isEnabled)
            binding.blueButton.isEnabled = !(binding.blueButton.isEnabled)
            binding.yellowButton.isEnabled = !(binding.yellowButton.isEnabled)
        }
    }

    private fun saveGame(level : Int, score : Int, lives : Int, sequence : String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Save game and quit ?")
        builder.setItems(arrayOf("yes", "no")) { _, which ->
            when (which) {
                0 -> {
                    // Sauve la partie et retourne au menu
                    GlobalScope.launch(Dispatchers.IO) {
                        game.setSeq(game.getSeq())
                        gameViewModel.saveGame(game)
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

    private fun initValues() {
        var seq = gameViewModel.getSequence()
        game = Game(gameViewModel.getLevel(), gameViewModel.getScore(), gameViewModel.getLives())
        game.setSeq(seq)

        for (i in 0 until seq.length) {
            inputsToFollow.add(gameViewModel.getSequence()[i].toString().toInt())
        }
    }

    private fun initButtons() {
        binding.redButton.setImageBitmap(bitmaps[0])
        binding.greenButton.setImageBitmap(bitmaps[1])
        binding.blueButton.setImageBitmap(bitmaps[2])
        binding.yellowButton.setImageBitmap(bitmaps[3])
    }
}