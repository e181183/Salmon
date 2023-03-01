package be.helmo.salmon.database.dao

import androidx.room.Query
import be.helmo.salmon.Game
import java.util.*

interface GameDao {
    @Query("SELECT * FROM game WHERE id = (:id)")
    fun replaceSavedGame(id: UUID): Game

    @Query("SELECT * FROM game WHERE id = (:id)")
    fun getGame(id: UUID): Game
}
