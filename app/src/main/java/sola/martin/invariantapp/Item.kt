package sola.martin.invariantapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Item_table")
@Parcelize
data class Item(@PrimaryKey @ColumnInfo(name = "item") var title: String,
                @ColumnInfo(name = "startLong") var startLong: Long,
                @ColumnInfo(name = "endLong") var endLong: Long,
                @ColumnInfo(name = "tag") var tag: Int? = null) : Parcelable{

    override fun toString(): String {
        return """
            title: $title
            startTime: $startLong
            endTime: $endLong
            tag: $tag   
            
        """.trimIndent()
    }
}

