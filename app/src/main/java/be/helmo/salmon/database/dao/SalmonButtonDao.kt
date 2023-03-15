package be.helmo.salmon.database.dao

import androidx.room.*
import be.helmo.salmon.model.SalmonButton
import java.util.*
@Dao
interface SalmonButtonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addButton(salmonButton: SalmonButton)

    @Query("UPDATE salmonButton SET imagePath = NULL")
    fun resetImages()

    @Query("UPDATE salmonButton SET soundPath = NULL")
    fun resetSounds()

    @Query("SELECT COUNT(id) FROM salmonButton where id = (:id)")
    fun isButtonStored(id : Int): Int

    @Query("SELECT imagePath FROM salmonButton WHERE id = (:id)")
    fun getImagePath(id: Int): String?

    @Query("SELECT soundPath FROM salmonButton WHERE id = (:id)")
    fun getSoundPath(id: Int): String?

    @Query("SELECT COUNT(id) FROM salmonButton")
    fun getCountButton() : Int

    @Query("UPDATE salmonButton SET imagePath = (:img) WHERE id = (:id)")
    fun setButtonImage(id: Int, img: String)

    @Query("UPDATE salmonButton SET soundPath = (:snd) WHERE id = (:id)")
    fun setSoundPath(id: Int, snd: String)
}