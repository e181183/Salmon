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
    @ColumnInfo(name = "image") val image : Bitmap? = null,
    //val sound : File? = null
    @ColumnInfo(name = "sound") val sound : String? = null
)
