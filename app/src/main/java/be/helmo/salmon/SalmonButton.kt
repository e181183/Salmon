package be.helmo.salmon

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SalmonButton(
    @PrimaryKey
    val id : Int,
    val image : Int,
    val sound : Int
)
