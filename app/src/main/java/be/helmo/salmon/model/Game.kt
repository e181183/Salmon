package be.helmo.salmon.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "level") var level: Int,
    @ColumnInfo(name = "score") var score: Int,
    @ColumnInfo(name = "lives") var lives: Int,
    @ColumnInfo(name = "sequence") var sequence: String = "") {

    constructor(level: Int, score: Int, lives: Int) : this(1, level, score, lives, "")
    constructor(level: Int, score: Int, lives: Int, sequence: String) : this(1, level, score, lives, sequence)

    fun getNiveau() : Int {
        return this.level
    }

    fun getSc() : Int {
        return this.score
    }

    fun getVies() : Int {
        return this.lives
    }

    fun getSeq() : String {
        return  this.sequence
    }

    fun setSeq(seq: String) {
        this.sequence = seq
    }

    fun upgradeLevel() {
        this.level += 1;
    }

    fun downgradeLives() {
        this.lives--
    }

    fun upgradeScore(sup : Int) {
        this.score += sup
    }
}




