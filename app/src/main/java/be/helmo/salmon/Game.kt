package be.helmo.salmon

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Game(
    @PrimaryKey
    val id : Int,
    val level: Int,
    val score: Int,
    val sequence: List<Int>
)