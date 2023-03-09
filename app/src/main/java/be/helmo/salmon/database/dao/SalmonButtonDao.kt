package be.helmo.salmon.database.dao

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.room.*
import be.helmo.salmon.model.SalmonButton
import java.util.*
@Dao
interface SalmonButtonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addButton(salmonButton: SalmonButton)

    //@Query("SELECT * FROM salmonButton WHERE id = (:id)")
    //fun getButton(id: Int): LiveData<SalmonButton>

    @Query("SELECT image FROM salmonButton WHERE id = (:id)")
    fun getButtonImage(id: Int): Bitmap

    @Query("SELECT COUNT(id) FROM salmonButton")
    fun getCountButton() : Int
}