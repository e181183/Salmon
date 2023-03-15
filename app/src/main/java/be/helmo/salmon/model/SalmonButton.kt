package be.helmo.salmon.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity
data class SalmonButton(
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "imagePath") val imagePath : String? = null,
    @ColumnInfo(name = "soundPath") val soundPath : String? = null
)
