package sola.martin.invariantapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item_table")
data class Item(@PrimaryKey @ColumnInfo(name = "item") var title: String,
                @ColumnInfo(name = "startDecimal") var startDecimal: Double,
                @ColumnInfo(name = "endDecimal") var endDecimal: Double,
                @ColumnInfo(name = "tag") var tag: Integer? = null)
