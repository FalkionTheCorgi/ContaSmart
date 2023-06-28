package com.example.accountspayable.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.accountspayable.Room.Item.ItemDao
import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.Room.Summary.SummaryDao
import com.example.accountspayable.Room.Summary.SummaryEntity

@Database(
    entities = [ItemEntity::class, SummaryEntity::class], version = 5
)
abstract class DataBase : RoomDatabase(){

    abstract fun item(): ItemDao
    abstract fun summary(): SummaryDao

    companion object {

        val MIGRATION_4_5 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE items ADD COLUMN icon TEXT")
            }
        }

        val migration = arrayOf(MIGRATION_4_5)

        @Volatile
        private var Instance: DataBase? = null

        fun getDataBase(context: Context): DataBase{

            return Instance ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "databaseContaSmart"
                ).build()
                Instance = instance
                instance
            }

        }

    }

}