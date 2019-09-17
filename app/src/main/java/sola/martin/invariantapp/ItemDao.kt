package sola.martin.invariantapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {



    @Query("SELECT * from item_table ORDER BY item DESC")
    fun getDescItemsByStart(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()
}