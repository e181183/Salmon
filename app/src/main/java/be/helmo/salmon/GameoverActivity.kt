package be.helmo.salmon

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class GameoverActivity : AppCompatActivity() {

    private lateinit var backToMenuButton: Button
    private lateinit var finalScoreText: TextView

    private lateinit var finalLevelText: TextView

    private lateinit var highScoreText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover)

        backToMenuButton = findViewById(R.id.GameOverButton)
        finalScoreText = findViewById(R.id.finalScore)
        finalLevelText = findViewById(R.id.finalLEvel)
        highScoreText = findViewById(R.id.highScore)

        finalScoreText.text = getString(R.string.actual_score) + intent.getIntExtra("SCORE", 0).toString()
        finalLevelText.text = getString(R.string.niveau) + intent.getIntExtra("NIVEAU", 0).toString()

        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var highScore = sharedPreferences.getInt("high_score_key", 0)

        highScoreText.text = getString(R.string.highscore) + highScore.toString()

        finalScoreText.text
        backToMenuButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}