package be.helmo.salmon.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.helmo.salmon.SalmonButton
import java.util.*

interface SalmonButtonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addButton(salmonButton: SalmonButton)

    @Query("DELETE * FROM salmonButton")
    fun resetButtons()

    @Query("SELECT * FROM salmonButton WHERE id = (:id)")
    fun getButton(id: Int): LiveData<SalmonButton>
}