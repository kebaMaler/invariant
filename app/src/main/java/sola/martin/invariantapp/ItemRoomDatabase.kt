package sola.martin.invariantapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Item::class], version = 1)
abstract  class ItemRoomDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ItemRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(ItemDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class ItemDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.itemDao())
                    }
                }
            }
        }


        suspend fun populateDatabase(itemDao: ItemDao) {

            itemDao.deleteAll()

            for (i in 0..25 ) {
                var item = Item("",0.0, 0.0)
                item.title = "Item $i"
                item.startDecimal= Random().nextInt(50).toDouble().plus(Random().nextDouble())
                item.endDecimal = item.startDecimal.plus(Random().nextDouble())
                itemDao.insert(item)

            }
        }
    }

}
