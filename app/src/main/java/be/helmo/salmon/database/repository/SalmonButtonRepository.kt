package be.helmo.salmon.database.repository

import be.helmo.salmon.SalmonButton
import be.helmo.salmon.database.dao.SalmonButtonDao

class SalmonButtonRepository(private val salmonButtonDao: SalmonButtonDao) {

    fun addButton(salmonButton : SalmonButton) {
        salmonButtonDao.addButton(salmonButton)
    }

    fun replaceSavedButton(){
        salmonButtonDao.replaceButton(1)
    }

    fun getButton() : SalmonButton {
        return salmonButtonDao.getButton(1)
    }
}