package be.helmo.salmon.database.dao

import androidx.room.Query
import be.helmo.salmon.SalmonButton
import java.util.*

interface SalmonButtonDao {
    @Query("SELECT * FROM game WHERE id = (:id)")
    fun replaceSavedGame(id: UUID): SalmonButton

    @Query("SELECT * FROM game WHERE id = (:id)")
    fun getGame(id: UUID): SalmonButton
}