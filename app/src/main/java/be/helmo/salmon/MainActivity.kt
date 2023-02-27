package be.helmo.salmon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var playGameButton: Button
    private lateinit var loadGameButton: Button
    private lateinit var customButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playGameButton = findViewById(R.id.play_game_button)
        loadGameButton = findViewById(R.id.load_game_button)
        customButton = findViewById(R.id.custom_button)

        playGameButton.setOnClickListener { view: View ->
            Toast.makeText(this, R.string.play_game, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
        loadGameButton.setOnClickListener { view: View ->
            Toast.makeText(this, R.string.load_game, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
        customButton.setOnClickListener { view: View ->
            Toast.makeText(this, R.string.customize, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CustomActivity::class.java)
            startActivity(intent)
        }
    }
}