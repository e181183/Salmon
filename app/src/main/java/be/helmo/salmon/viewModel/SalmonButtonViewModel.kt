package be.helmo.salmon.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import be.helmo.salmon.database.SalmonButtonDatabase
import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.repository.SalmonButtonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SalmonButtonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : SalmonButtonRepository

    init {
        val salmonButtonDao = SalmonButtonDatabase.getDatabase(application).SalmonButtonDao()
        repository = SalmonButtonRepository(salmonButtonDao)
    }

    fun addButtonToDb(salmonButton : SalmonButton) {
        viewModelScope.launch(Dispatchers.IO) {
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
        if (repository.getImagePath(id) != null) {
            return repository.getImagePath(id)
        } else {
            return null
        }
    }
    fun getSoundPath(id : Int) : String? {
        return repository.getSoundPath(id)
    }

    fun getCountButton() : Int {
        return repository.getCountButton()
    }

    fun setButtonImage(id: Int, img: String) {
        repository.setButtonImage(id, img)
    }

    fun setSoundPath(id: Int, snd: String) {
        repository.setSoundPath(id, snd)
    }

}