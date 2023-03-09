package be.helmo.salmon.database.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.SalmonButtonDatabase
import be.helmo.salmon.database.dao.SalmonButtonDao

class SalmonButtonRepository(private val salmonButtonDao : SalmonButtonDao) {

    suspend fun addButton(salmonButton : SalmonButton) {
        salmonButtonDao.addButton(salmonButton)
    }

    //fun getButton(id : Int) : LiveData<SalmonButton> {
    //    return salmonButtonDao.getButton(id)
    //}

    fun getButtonImage(id : Int) : Bitmap {
        return salmonButtonDao.getButtonImage(id)
    }

    fun getCountButton() : Int{
        return salmonButtonDao.getCountButton()
    }

}