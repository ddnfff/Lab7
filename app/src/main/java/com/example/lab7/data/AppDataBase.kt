package com.example.lab7.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab7.model.ServiceRecordEntity

@Database(
    entities = [ServiceRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun serviceRecordDao(): ServiceRecordDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "car-service-db"
            ).build()
        }
    }
}