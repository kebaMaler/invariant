package sola.martin.invariantapp

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao){

    val allItems: LiveData<List<Item>> = itemDao.getAscItemsByStart()

    suspend fun insert(item: Item){
        itemDao.insert(item)
    }

    suspend fun  deleteItem(item: Item){
        itemDao.deleteItem(item)
    }
}