package be.helmo.salmon

import android.content.Context
import android.content.Intent

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import be.helmo.salmon.databinding.ActivityMainBinding
import be.helmo.salmon.viewModel.GameViewModel
import be.helmo.salmon.viewModel.SalmonButtonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var buttonViewmodel : SalmonButtonViewModel
    private lateinit var gameViewModel: GameViewModel
    private var isLoadGameActive : Boolean = false

    private lateinit var binding: ActivityMainBinding

    private lateinit var sound : Sound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonViewmodel = ViewModelProvider(this)[SalmonButtonViewModel::class.java]
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        sound = Sound(this, buttonViewmodel)

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var highScore = sharedPreferences.getInt("high_score_key", 0)

        binding.highScoreMenu.text = getString(R.string.highscore) + highScore.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            if(gameViewModel.getCountGame() != 0) {
                isLoadGameActive = true
                binding.loadGameButton.backgroundTintList=
                    ColorStateList.valueOf(resources.getColor(R.color.salmon_orange))
            }
        }

        binding.muteButton.isChecked = sound.getIsMute()
        binding.muteButton.setOnClickListener{
            if(binding.muteButton.isChecked){
                sound.setIsMute(true)
            }else{
                sound.setIsMute(false)
            }
        }

        binding.playGameButton.setOnClickListener {
            if(isLoadGameActive) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Play a new game and delete the saved game ?")
                builder.setItems(arrayOf("yes", "no")) { _, which ->
                    when (which) {
                        0 -> {
                            // supprime la partie sauvegardée et en commence une nouvelle
                            GlobalScope.launch(Dispatchers.IO){
                                gameViewModel.deleteFinishedSavedGame()
                            }
                            val intent = Intent(this, PlayActivity::class.java)
                            intent.putExtra("isNew", true)
                            startActivity(intent)
                        }
                        1 -> {
                            // Annuler
                        }
                    }
                }
                builder.show()
            }else {
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("isNew", true)
                startActivity(intent)
            }
        }
        binding.loadGameButton.setOnClickListener {
            if (isLoadGameActive) {
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("isNew", false)
                startActivity(intent)
            } else {
                Toast.makeText(this, "There is no saved game", Toast.LENGTH_SHORT).show()
            }
        }
        binding.customButton.setOnClickListener {
            Toast.makeText(this, R.string.customize, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CustomActivity::class.java)
            startActivity(intent)
        }

    }
}