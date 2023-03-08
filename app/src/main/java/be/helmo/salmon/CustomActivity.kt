package be.helmo.salmon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import be.helmo.salmon.databinding.ActivityCustomBinding
import be.helmo.salmon.viewModel.SalmonButtonViewModel

class CustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomBinding

    private lateinit var imagePath : Uri
    private lateinit var imageToStore : Bitmap

    private lateinit var buttonViewmodel : SalmonButtonViewModel

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 88
        const val REQUEST_IMAGE_PICK = 99
        var companionButtonId: Int = 0

        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonViewmodel = ViewModelProvider(this).get(SalmonButtonViewModel::class.java)

        binding.resetButton.setOnClickListener {
            Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
            resetButtons()
        }
        //red(1)
        createOnClickListeners(binding.redCustomButton, binding.imageForRed, binding.soundForRed, 1)
        //green(2)
        createOnClickListeners(binding.greenCustomButton, binding.imageForGreen, binding.soundForGreen, 2)
        //blue(3)
        createOnClickListeners(binding.blueCustomButton, binding.imageForBlue, binding.soundForBlue, 3)
        //yellow(4)
        createOnClickListeners(binding.yellowCustomButton, binding.imageForYellow, binding.soundForYellow, 4)
    }

    private fun createOnClickListeners(custBut : View, imgForBut : View, sndForBut : View, buttonId: Int) {
        val mp : MediaPlayer = MediaPlayer.create(this, R.raw.test)
        custBut.setOnClickListener {
            Toast.makeText(this, custBut.contentDescription, Toast.LENGTH_SHORT).show()
            mp.start()
        }
        imgForBut.setOnClickListener {
            Toast.makeText(this, imgForBut.contentDescription, Toast.LENGTH_SHORT).show()
            companionButtonId = buttonId
            takeOrPickPhoto()
        }
        sndForBut.setOnClickListener {
            Toast.makeText(this, sndForBut.contentDescription, Toast.LENGTH_SHORT).show()
        }
    }
    private fun resetButtons() {
        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_red_button)
        buttonViewmodel.addButtonToDb(1,imageToStore, "res")
        binding.redCustomButton.setImageBitmap(imageToStore)

        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_green_button)
        buttonViewmodel.addButtonToDb(2,imageToStore, "res")
        binding.greenCustomButton.setImageBitmap(imageToStore)

        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_blue_button)
        buttonViewmodel.addButtonToDb(3,imageToStore, "res")
        binding.blueCustomButton.setImageBitmap(imageToStore)

        imageToStore = BitmapFactory.decodeResource(resources, R.drawable.sound_yellow_button)
        buttonViewmodel.addButtonToDb(4,imageToStore, "res")
        binding.yellowCustomButton.setImageBitmap(imageToStore)
    }

    private fun takeOrPickPhoto() {
        if (allPermissionsGranted()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choisir une image")
            builder.setItems(arrayOf("Prendre une photo", "Choisir dans la galerie")) { _, which ->
                when (which) {
                    0 -> {
                        // Ouvre la caméra pour prendre une photo
                        val intentPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intentPhoto, REQUEST_IMAGE_CAPTURE)
                    }
                    1 -> {
                        // Ouvre la galerie pour choisir une photo
                        choseImage()
                    }
                }
            }
            builder.show()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
            takeOrPickPhoto()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun choseImage() {
        try {
            intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, REQUEST_IMAGE_PICK)

        } catch (exc: Exception){
            Toast.makeText(this, "fail chose", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            // caméra
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
                val imageBitmap  = data.extras?.get("data") as Bitmap
                imageToStore = imageBitmap
                displayImage(companionButtonId)
            // galerie
            } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.data != null) {
                imagePath = data.data!!
                imageToStore = MediaStore.Images.Media.getBitmap(contentResolver,imagePath)
                displayImage(companionButtonId)
            }
        } catch (exc: Exception){
                Toast.makeText(this, "fail result", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayImage(buttonId: Int) {
        when (buttonId) {
            1 ->  binding.redCustomButton.setImageBitmap(imageToStore)
            2 ->  binding.greenCustomButton.setImageBitmap(imageToStore)
            3 ->  binding.blueCustomButton.setImageBitmap(imageToStore)
            else ->  binding.yellowCustomButton.setImageBitmap(imageToStore)
        }
        buttonViewmodel.addButtonToDb(buttonId,imageToStore, "photo")
    }
}