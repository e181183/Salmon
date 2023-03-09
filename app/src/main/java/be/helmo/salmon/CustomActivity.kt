package be.helmo.salmon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomBinding

    private lateinit var imagePath : Uri
    private lateinit var imageToStore : Bitmap

    private lateinit var buttonViewmodel : SalmonButtonViewModel

    private var micro : Micro = Micro(this)

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 88
        const val REQUEST_IMAGE_PICK = 99
        private const val REQUEST_CODE_PERMISSIONS = 10

        private val REQUIRED_ALL_PERMISSIONS =
            mutableListOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            ).toTypedArray()

        private val REQUIRED_CAM_PERMISSION =
            mutableListOf(
                Manifest.permission.CAMERA,
            ).toTypedArray()

        var companionButtonId: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this, REQUIRED_ALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )

        buttonViewmodel = ViewModelProvider(this).get(SalmonButtonViewModel::class.java)

        GlobalScope.launch {
            getImagesFromDb()
        }

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

        //val mp : MediaPlayer = MediaPlayer.create(this, R.raw.test)
        custBut.setOnClickListener {
            //mp.start()
            micro.playAudio(micro.choseMediaPlayer(buttonId), buttonId)
        }
        imgForBut.setOnClickListener {
            companionButtonId = buttonId
            takeOrPickPhoto()
        }
        sndForBut.setOnClickListener {
            if (micro.microPermissionsGranted()) {
                if (!micro.getIsRecording()) {
                    micro.recordAudio(buttonId)
                    sndForBut.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                } else {
                    micro.stopRecord()
                    sndForBut.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.salmon_orange))
                }
            } else {
                Toast.makeText(this, "please, enable micro permission", Toast.LENGTH_SHORT).show()
            }

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
        if (cameraPermissionsGranted()) {
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
            Toast.makeText(this, "please, enable camera permission", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cameraPermissionsGranted() = REQUIRED_CAM_PERMISSION.all {
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

    private fun getImagesFromDb() {
        binding.redCustomButton.setImageBitmap(buttonViewmodel.getButtonImage(1))
        binding.greenCustomButton.setImageBitmap(buttonViewmodel.getButtonImage(2))
        binding.blueCustomButton.setImageBitmap(buttonViewmodel.getButtonImage(3))
        binding.yellowCustomButton.setImageBitmap(buttonViewmodel.getButtonImage(4))
    }
}