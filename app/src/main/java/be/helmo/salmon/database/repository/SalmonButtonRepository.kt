package be.helmo.salmon.database.repository

import be.helmo.salmon.model.SalmonButton
import be.helmo.salmon.database.dao.SalmonButtonDao

class SalmonButtonRepository(private val salmonButtonDao : SalmonButtonDao) {

    suspend fun addButton(salmonButton : SalmonButton) {
        salmonButtonDao.addButton(salmonButton)
    }

    fun resetImages() {
        salmonButtonDao.resetImages()
    }

    fun resetSounds() {
        salmonButtonDao.resetSounds()
    }

    fun isButtonStored(id: Int) : Int{
        return salmonButtonDao.isButtonStored(id)
    }

    fun getImagePath(id : Int) : String? {
        return salmonButtonDao.getImagePath(id)
    }

    fun getSoundPath(id : Int) : String? {
        return salmonButtonDao.getSoundPath(id)
    }

    fun getCountButton() : Int{
        return salmonButtonDao.getCountButton()
    }

    fun setButtonImage(id: Int, img: String) {
        salmonButtonDao.setButtonImage(id, img)
    }

    fun setSoundPath(id: Int, snd: String) {
        salmonButtonDao.setSoundPath(id, snd)
    }
}