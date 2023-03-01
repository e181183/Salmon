package be.helmo.salmon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.helmo.salmon.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_play)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}