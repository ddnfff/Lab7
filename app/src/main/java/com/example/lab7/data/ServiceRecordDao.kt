package com.example.lab7.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab7.model.ServiceRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceRecordDao {

    @Query("SELECT * FROM service_records ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ServiceRecordEntity>>

    @Query("SELECT * FROM service_records WHERE id = :id")
    suspend fun getById(id: Int): ServiceRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ServiceRecordEntity)

    @Update
    suspend fun update(record: ServiceRecordEntity)

    @Delete
    suspend fun delete(record: ServiceRecordEntity)
}