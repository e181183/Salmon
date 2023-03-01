package be.helmo.salmon.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.helmo.salmon.Game
import java.util.*

interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSavedGame(game: Game)

    @Delete
    fun deleteSavedGame(game: Game)

    @Query("SELECT * FROM game WHERE id = (:id)")
    fun getGame(id: Int): LiveData<Game>
}
