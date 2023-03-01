package be.helmo.salmon.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.helmo.salmon.Game
import be.helmo.salmon.SalmonButton
import java.util.*

interface SalmonButtonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addButton(salmonButton: SalmonButton)

    @Query("""UPDATE salmonButton 
        SET image = (:image),
        sound = (:sound)
        WHERE id = (:id)""")
    fun replaceButton(id: Int): SalmonButton

    @Query("SELECT * FROM game WHERE id = (:id)")
    fun getButton(id: Int): SalmonButton
}