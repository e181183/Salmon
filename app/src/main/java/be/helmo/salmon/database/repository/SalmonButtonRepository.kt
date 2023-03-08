package be.helmo.salmon.database.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.SalmonButtonDatabase
import be.helmo.salmon.database.dao.SalmonButtonDao

class SalmonButtonRepository(private val salmonButtonDao : SalmonButtonDao) {

    //private val salmonButtonDao : SalmonButtonDao = SalmonButtonDatabase.getInstance()!!.SalmonButtonDao()

    suspend fun addButton(salmonButton : SalmonButton) {
        salmonButtonDao.addButton(salmonButton)
    }

    /*suspend fun resetButtons() {
        salmonButtonDao.resetButtons()
    }*/

    fun getButton(id : Int) : LiveData<SalmonButton> {
        return salmonButtonDao.getButton(id)
    }

    fun getButtonImage(id : Int) : Bitmap {
        return salmonButtonDao.getButtonImage(id)
    }

    /*companion object {
        private var instance : SalmonButtonRepository? = null
        fun getInstance(): SalmonButtonRepository? {
            if (instance == null) {
                instance = SalmonButtonRepository()
            }
            return instance
        }
    }*/
}