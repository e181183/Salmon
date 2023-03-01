package be.helmo.salmon.database.repository

import androidx.lifecycle.LiveData
import be.helmo.salmon.SalmonButton
import be.helmo.salmon.database.SalmonButtonDatabase
import be.helmo.salmon.database.dao.SalmonButtonDao

class SalmonButtonRepository{

    private val salmonButtonDao : SalmonButtonDao = SalmonButtonDatabase.getInstance()!!.SalmonButtonDao()

    fun addButton(salmonButton : SalmonButton) {
        salmonButtonDao.addButton(salmonButton)
    }

    fun resetButtons() {
        salmonButtonDao.resetButtons()
    }

    fun getButton() : LiveData<SalmonButton> {
        return salmonButtonDao.getButton(1)
    }

    companion object {
        private var instance : SalmonButtonRepository? = null
        fun getInstance(): SalmonButtonRepository? {
            if (instance == null) {
                instance = SalmonButtonRepository()
            }
            return instance
        }
    }
}