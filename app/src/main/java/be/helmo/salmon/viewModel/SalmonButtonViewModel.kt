package be.helmo.salmon.viewModel

import android.app.Application
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.helmo.salmon.database.SalmonButtonDatabase
import be.helmo.salmon.database.dao.SalmonButtonDao
import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.repository.SalmonButtonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    fun addButtonToDb(id: Int, image : Bitmap, sound: String) {
        val salmonButton = SalmonButton(id, image, sound)
        addButtonToDb(salmonButton)
    }

    fun getButton(id : Int) : LiveData<SalmonButton> {
        return repository.getButton(id)
    }

    fun getButtonImage(id : Int) : Bitmap {
        return repository.getButtonImage(id)
    }

}