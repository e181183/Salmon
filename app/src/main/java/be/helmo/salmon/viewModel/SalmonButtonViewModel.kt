package be.helmo.salmon.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.helmo.salmon.SalmonButton
import be.helmo.salmon.database.repository.SalmonButtonRepository

class SalmonButtonViewModel : ViewModel() {

    fun addButton(salmonButton : SalmonButton) {
        SalmonButtonRepository.getInstance()!!.addButton(salmonButton)
    }

    fun resetButtons() {
        SalmonButtonRepository.getInstance()!!.resetButtons()
    }

    fun getButton() : LiveData<SalmonButton> {
        return SalmonButtonRepository.getInstance()!!.getButton()
    }

}