package com.example.lab7.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab7.model.WishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {

    @Query("SELECT * FROM wishes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<WishEntity>>

    @Query("SELECT * FROM wishes WHERE id = :id")
    suspend fun getById(id: Int): WishEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wish: WishEntity)

    @Update
    suspend fun update(wish: WishEntity)

    @Delete
    suspend fun delete(wish: WishEntity)
}