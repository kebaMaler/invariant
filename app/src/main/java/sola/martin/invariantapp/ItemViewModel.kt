package sola.martin.invariantapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application){

    private val repository : ItemRepository
    val allItems: LiveData<List<Item>>


    init {
        val itemDao = ItemRoomDatabase.getDatabase(application,viewModelScope).itemDao()
        repository = ItemRepository(itemDao)
        allItems = repository.allItems
    }

    fun insert(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }

}