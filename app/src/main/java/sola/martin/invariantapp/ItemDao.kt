package sola.martin.invariantapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete



@Dao
interface ItemDao {



    @Query("SELECT * from item_table ORDER BY startLong ASC")
    fun getAscItemsByStart(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Delete
    fun deleteItem(item: Item)


    @Query("DELETE FROM item_table")
    suspend fun deleteAll()
}