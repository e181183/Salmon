package be.helmo.salmon

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import be.helmo.salmon.databinding.ActivityMainBinding
import be.helmo.salmon.viewModel.SalmonButtonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var buttonViewmodel : SalmonButtonViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonViewmodel = ViewModelProvider(this).get(SalmonButtonViewModel::class.java)

        GlobalScope.launch {
            if(buttonViewmodel.getCountButton() == 0) {
                initButtons()
            }
        }

        binding.playGameButton.setOnClickListener {
            Toast.makeText(this, R.string.play_game, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
        binding.loadGameButton.setOnClickListener {
            Toast.makeText(this, R.string.load_game, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
        binding.customButton.setOnClickListener {
            Toast.makeText(this, R.string.customize, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CustomActivity::class.java)
            startActivity(intent)
        }

        binding.mute.setOnCheckedChangeListener({_, isChecked ->
            if(isChecked) {

            }
        })
    }

    private fun initButtons() {
        var imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_red_button)
        buttonViewmodel.addButtonToDb(1,imageToStore, "res")

        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_green_button)
        buttonViewmodel.addButtonToDb(2,imageToStore, "res")

        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_blue_button)
        buttonViewmodel.addButtonToDb(3,imageToStore, "res")

        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_yellow_button)
        buttonViewmodel.addButtonToDb(4,imageToStore, "res")
    }
}