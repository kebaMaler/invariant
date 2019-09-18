package sola.martin.invariantapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item_table")
data class Item(@PrimaryKey @ColumnInfo(name = "item") var title: String,
                @ColumnInfo(name = "startLong") var startLong: Long,
                @ColumnInfo(name = "endLong") var endLong: Long,
                @ColumnInfo(name = "tag") var tag: Integer? = null)
