package be.helmo.salmon.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.helmo.salmon.model.Game
import java.util.*

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSavedGame(game: Game)

    @Delete
    fun deleteSavedGame(game: Game)

    @Query("SELECT * FROM game WHERE id = (:id)")
    fun getGame(id: Int): LiveData<Game>
}
