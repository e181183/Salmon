package be.helmo.salmon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import be.helmo.salmon.databinding.ActivityPlayBinding
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding

    private lateinit var redGameButton: ImageButton
    private lateinit var greenGameButton: ImageButton
    private lateinit var blueGameButton: ImageButton
    private lateinit var yellowGameButton: ImageButton
    private  val inputsToFollow = mutableListOf<Int>()
    private val niveau = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        redGameButton = findViewById(R.id.red_button)
        greenGameButton = findViewById(R.id.green_button)
        blueGameButton = findViewById(R.id.blue_button)
        yellowGameButton = findViewById(R.id.yellow_button)


        redGameButton.setOnClickListener() {
            verifyInput(0)
        }

        greenGameButton.setOnClickListener() {
            verifyInput(1)
        }

        blueGameButton.setOnClickListener() {
            verifyInput(2)
        }

        yellowGameButton.setOnClickListener() {
            verifyInput(3)
        }

        Timer().schedule(2000) {startGame()}

    }

    private fun startGame() {
        inputsToFollow.add(pickAnInput())

        for(i in inputsToFollow) {
            displayInput(i);
        }

    }

    private fun displayInput(input: Int) {
        when(input) {
            0 ->{redGameButton.setImageResource(R.drawable.sound_red_button)
                Timer().schedule(1000){redGameButton.setImageResource(R.drawable.bouton_base_rouge)}
            }

            1-> {greenGameButton.setImageResource(R.drawable.sound_green_button)
                Timer().schedule(1000){redGameButton.setImageResource(R.drawable.bouton_base_vert)}
            }

            2 -> {blueGameButton.setImageResource(R.drawable.sound_blue_button)
                Timer().schedule(1000){redGameButton.setImageResource(R.drawable.bouton_base_bleu)}
            }
            3-> {yellowGameButton.setImageResource(R.drawable.sound_yellow_button)
                Timer().schedule(1000){redGameButton.setImageResource(R.drawable.bouton_base_jaune)}
            }
        }
    }

    private fun pickAnInput() : Int {
         return (0..3).random();
    }

    private fun verifyInput(input: Int) {
        if(input == inputsToFollow.get(niveau)){
            
        }
    }


}