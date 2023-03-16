package be.helmo.salmon.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.helmo.salmon.model.Game
import java.util.*

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSavedGame(game: Game)

    @Query("DELETE FROM game WHERE id = 1")
    fun deleteSavedGame()

    @Query("SELECT * FROM game WHERE id = 1")
    fun getGame(): LiveData<Game>

    @Query("SELECT level FROM game WHERE id = 1")
    fun getLevel(): Int

    @Query("SELECT score FROM game WHERE id = 1")
    fun getScore(): Int

    @Query("SELECT lives FROM game WHERE id = 1")
    fun getLives(): Int

    @Query("SELECT sequence FROM game WHERE id = 1")
    fun getSequence(): String

    @Query("SELECT COUNT(id) FROM game")
    fun getCountGame() : Int
}
