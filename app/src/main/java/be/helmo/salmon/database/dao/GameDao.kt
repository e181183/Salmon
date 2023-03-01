package be.helmo.salmon.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.helmo.salmon.Game
import java.util.*

interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSavedGame(game: Game)

    @Query("""UPDATE game 
        SET level = (:level),
        score = (:score),
        sequence = (:sequence)
        WHERE id = (:id)""")
    fun replaceSavedGame(id: Int): Game

    @Query("SELECT * FROM game WHERE id = (:id)")
    fun getGame(id: Int): Game
}
