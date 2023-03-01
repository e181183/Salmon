package be.helmo.salmon

import android.os.Bundle
import android.view.View
import android.widget.Button
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

        binding.resetButton.setOnClickListener { view: View ->
            Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
        }

        binding.redButton.setOnClickListener { view: View ->
            Toast.makeText(this, "red", Toast.LENGTH_SHORT).show()
        }
        binding.imageForRed.setOnClickListener { view: View ->
            Toast.makeText(this, "red image", Toast.LENGTH_SHORT).show()
        }
        binding.soundForRed.setOnClickListener { view: View ->
            Toast.makeText(this, "red sound", Toast.LENGTH_SHORT).show()
        }

        binding.greenButton.setOnClickListener { view: View ->
            Toast.makeText(this, "green", Toast.LENGTH_SHORT).show()
        }
        binding.imageForGreen.setOnClickListener { view: View ->
            Toast.makeText(this, "green image", Toast.LENGTH_SHORT).show()
        }
        binding.soundForGreen.setOnClickListener { view: View ->
            Toast.makeText(this, "green sound", Toast.LENGTH_SHORT).show()
        }

        binding.blueButton.setOnClickListener { view: View ->
            Toast.makeText(this, "blue", Toast.LENGTH_SHORT).show()
        }
        binding.imageForBlue.setOnClickListener { view: View ->
            Toast.makeText(this, "blue image", Toast.LENGTH_SHORT).show()
        }
        binding.soundForBlue.setOnClickListener { view: View ->
            Toast.makeText(this, "blue sound", Toast.LENGTH_SHORT).show()
        }

        binding.yellowButton.setOnClickListener { view: View ->
            Toast.makeText(this, "yellow", Toast.LENGTH_SHORT).show()
        }
        binding.imageForYellow.setOnClickListener { view: View ->
            Toast.makeText(this, "yellow image", Toast.LENGTH_SHORT).show()
        }
        binding.soundForYellow.setOnClickListener { view: View ->
            Toast.makeText(this, "yellow sound", Toast.LENGTH_SHORT).show()
        }
    }
}