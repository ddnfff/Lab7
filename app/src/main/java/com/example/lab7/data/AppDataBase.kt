package com.example.lab7.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab7.model.WishEntity

@Database(
    entities = [WishEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wishDao(): WishDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "wishlist-db"
            ).build()
        }
    }
}