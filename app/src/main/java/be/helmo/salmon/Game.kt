package be.helmo.salmon

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Game(
    @PrimaryKey
    val id : UUID,
    val level: Int,
    val score: Int,
    val sequence: List<Int>
)