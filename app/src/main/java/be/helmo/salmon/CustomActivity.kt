package be.helmo.salmon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.helmo.salmon.databinding.ActivityCustomBinding

class CustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_custom)
        binding = ActivityCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.redButton.setOnClickListener { view: View ->
            Toast.makeText(this, "red", Toast.LENGTH_SHORT).show()
        }
        binding.greenButton.setOnClickListener { view: View ->
            Toast.makeText(this, "green", Toast.LENGTH_SHORT).show()
        }
        binding.blueButton.setOnClickListener { view: View ->
            Toast.makeText(this, "blue", Toast.LENGTH_SHORT).show()
        }
        binding.yellowButton.setOnClickListener { view: View ->
            Toast.makeText(this, "yellow", Toast.LENGTH_SHORT).show()
        }
    }
}