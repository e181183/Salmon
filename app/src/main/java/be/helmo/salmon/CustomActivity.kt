package be.helmo.salmon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class CustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomBinding

    private lateinit var imagePath : Uri
    private lateinit var imagePathString : String
    private lateinit var imageToStore : Bitmap

    private lateinit var buttonViewmodel : SalmonButtonViewModel

    private lateinit var sound : Sound

    private var bitmaps = mutableListOf<Bitmap>()

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 88
        const val REQUEST_IMAGE_PICK = 99
        private const val REQUEST_CODE_PERMISSIONS = 10

        private val REQUIRED_ALL_PERMISSIONS =
            mutableListOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
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

        buttonViewmodel = ViewModelProvider(this)[SalmonButtonViewModel::class.java]

        sound = Sound(this, buttonViewmodel)

        bitmaps = mutableListOf(
            BitmapFactory.decodeResource(resources, R.drawable.sound_red_button),
            BitmapFactory.decodeResource(resources, R.drawable.sound_green_button),
            BitmapFactory.decodeResource(resources, R.drawable.sound_blue_button),
            BitmapFactory.decodeResource(resources, R.drawable.sound_yellow_button)
        )

        GlobalScope.launch(Dispatchers.IO) {
            getImagesFromDb()
            withContext(Dispatchers.Main) {
                binding.redCustomButton.setImageBitmap(bitmaps[0])
                binding.greenCustomButton.setImageBitmap(bitmaps[1])
                binding.blueCustomButton.setImageBitmap(bitmaps[2])
                binding.yellowCustomButton.setImageBitmap(bitmaps[3])
            }
        }

        binding.resetImages.setOnClickListener {
            Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
            resetButtonsImages()
        }
        binding.resetSounds.setOnClickListener {
            Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
            sound.resetAllSounds()
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

        custBut.setOnClickListener {
            sound.playAudio(buttonId)
        }
        imgForBut.setOnClickListener {
            companionButtonId = buttonId
            takeOrPickPhoto()
        }
        sndForBut.setOnClickListener {
            if (sound.microPermissionsGranted()) {
                if (!sound.getIsRecording()) { //enregistre le son
                    sound.recordAudio(buttonId)
                    sndForBut.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))

                } else if(sndForBut.backgroundTintList == ColorStateList.valueOf(resources.getColor(R.color.red))) {
                    //coupe l'enregistrement si le bouton de son correspond au bon SalmonButton
                    sound.stopRecord(buttonId)
                    sndForBut.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.salmon_orange))
                } else {
                    Toast.makeText(this, "only one record at a time", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "please, enable micro permission", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun resetButtonsImages() {
        binding.redCustomButton.setImageResource(R.drawable.sound_red_button)
        binding.greenCustomButton.setImageResource(R.drawable.sound_green_button)
        binding.blueCustomButton.setImageResource(R.drawable.sound_blue_button)
        binding.yellowCustomButton.setImageResource(R.drawable.sound_yellow_button)
        GlobalScope.launch(Dispatchers.IO) {
          buttonViewmodel.resetImages()
        }
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

    private fun cameraPermissionsGranted() : Boolean {
        return ContextCompat.checkSelfPermission(baseContext, REQUIRED_ALL_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED
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
        imagePathString = saveImageToInternalStorage(imageToStore, buttonId)
        GlobalScope.launch(Dispatchers.IO) {
            if (buttonViewmodel.isButtonStored(buttonId) != 0) {
                buttonViewmodel.setButtonImage(buttonId, imagePathString)
            } else {
                buttonViewmodel.addButtonToDb(buttonId, imagePathString, null)
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap, buttonId: Int): String {
        val directory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = "salmonButtonImage_$buttonId.jpg"
        val file = File(directory, fileName)

        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        stream.flush()
        stream.close()

        return file.absolutePath
    }

    private fun getImagesFromDb() {
        for (i in 1..4) {
            if (buttonViewmodel.getImagePath(i) != null){
                val file = File(buttonViewmodel.getImagePath(i))
                if (file.exists()) {
                    bitmaps[i - 1] = BitmapFactory.decodeFile(file.absolutePath)
                }
            }
        }
    }

}