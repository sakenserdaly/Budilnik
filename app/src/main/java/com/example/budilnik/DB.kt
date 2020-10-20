package com.example.budilnik

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getItemsDao(): ItemDao

    companion object {

        private const val DB_NAME = "budilnik.db"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context):AppDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
            }

            return instance
        }
    }
}