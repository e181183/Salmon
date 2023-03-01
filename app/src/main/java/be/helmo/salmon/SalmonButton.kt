package be.helmo.salmon

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SalmonButton(
    @PrimaryKey
    val id : UUID,
    val image : Int,
    val Sound : Int
)
