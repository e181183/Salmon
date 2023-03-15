package be.helmo.salmon.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "level") val level: Int,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "lifes") val lifes: Int,
    @ColumnInfo(name = "sequence") val sequence: String
)