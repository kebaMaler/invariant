package sola.martin.invariantapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.String.format
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

@Database(entities = [Item::class], version = 2)
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

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.itemDao())
                    }
                }
            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(itemDao: ItemDao) {

            itemDao.deleteAll()


            for (i in 0..25) {
                var item = Item("",0, 0)

                item.title= "Item $i"
              item.startLong= System.currentTimeMillis()- (10000000..20000000).random().toLong()
              item.endLong=  item.startLong.plus((0..100).random().toLong())

                itemDao.insert(item)
            }
        }
    }

}
