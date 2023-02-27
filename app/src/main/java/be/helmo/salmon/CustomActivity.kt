package be.helmo.salmon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import be.helmo.salmon.databinding.ActivityCustomBinding

class CustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
        binding = ActivityCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}