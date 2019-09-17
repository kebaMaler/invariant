package sola.martin.invariantapp

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao){

    val allItems: LiveData<List<Item>> = itemDao.getDescItemsByStart()

    suspend fun insert(item: Item){
        itemDao.insert(item)
    }
}