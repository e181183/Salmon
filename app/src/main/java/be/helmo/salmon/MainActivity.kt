package be.helmo.salmon

import android.content.Context
import android.content.Intent

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import be.helmo.salmon.databinding.ActivityMainBinding
import be.helmo.salmon.viewModel.GameViewModel
import be.helmo.salmon.viewModel.SalmonButtonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var buttonViewmodel : SalmonButtonViewModel
    private lateinit var gameViewModel: GameViewModel
    private var isLoadGameActive : Boolean = false

    private lateinit var binding: ActivityMainBinding

    private lateinit var highScoreText: TextView

    private lateinit var micro : Micro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        highScoreText = findViewById(R.id.highScoreMenu)

        buttonViewmodel = ViewModelProvider(this).get(SalmonButtonViewModel::class.java)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        micro = Micro(this, buttonViewmodel)

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var highScore = sharedPreferences.getInt("high_score_key", 0)

        highScoreText.text = getString(R.string.highscore) + highScore.toString()

        GlobalScope.launch {
            if(gameViewModel.getCountGame() != 0) {
                isLoadGameActive = true
                binding.loadGameButton.backgroundTintList=
                    ColorStateList.valueOf(resources.getColor(R.color.salmon_orange))
            }
        }

        binding.muteButton.isChecked = micro.getIsMute()
        binding.muteButton.setOnClickListener{
            if(binding.muteButton.isChecked){
                micro.setIsMute(true)
            }else{
                micro.setIsMute(false)
            }
        }

        binding.playGameButton.setOnClickListener {
            Toast.makeText(this, R.string.play_game, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlayActivity::class.java)
            intent.putExtra("isNew", true)
            startActivity(intent)
        }
        binding.loadGameButton.setOnClickListener {
            if (isLoadGameActive) {
                Toast.makeText(this, R.string.load_game, Toast.LENGTH_SHORT).show()
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