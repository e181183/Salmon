package be.helmo.salmon.viewModel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import be.helmo.salmon.database.SalmonButtonDatabase
import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.repository.SalmonButtonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class SalmonButtonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : SalmonButtonRepository

    init {
        val salmonButtonDao = SalmonButtonDatabase.getDatabase(application).SalmonButtonDao()
        repository = SalmonButtonRepository(salmonButtonDao)
    }

    private fun addButtonToDb(salmonButton : SalmonButton) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.addButton(salmonButton)
        }
    }

    fun addButtonToDb(id: Int, image : String?, snd: String?) {
        val salmonButton = SalmonButton(id, image, snd)
        addButtonToDb(salmonButton)
    }

    fun resetImages() {
        repository.resetImages()
    }

    fun resetSounds(){
        repository.resetSounds()
    }

    fun isButtonStored(id: Int) : Int{
        return repository.isButtonStored(id)
    }

    fun getImagePath(id : Int) : String? {
        return if (repository.getImagePath(id) != null) {
            repository.getImagePath(id)
        } else {
            null
        }
    }
    fun getSoundPath(id : Int) : String? {
        return repository.getSoundPath(id)
    }

    fun setButtonImage(id: Int, img: String) {
        repository.setButtonImage(id, img)
    }

    fun setSoundPath(id: Int, snd: String) {
        repository.setSoundPath(id, snd)
    }

    fun getImagesFromDb(bitmaps : MutableList<Bitmap>) {
        for (i in 1..4) {
            if (getImagePath(i) != null){
                val file = File(getImagePath(i))
                if (file.exists()) {
                    bitmaps[i - 1] = BitmapFactory.decodeFile(file.absolutePath)
                }
            }
        }
    }

}