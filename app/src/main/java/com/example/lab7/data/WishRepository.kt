package com.example.lab7.data

import com.example.lab7.model.WishEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishRepository @Inject constructor(
    private val dao: WishDao
) {
    fun getAllWishes(): Flow<List<WishEntity>> = dao.getAll()

    suspend fun getWishById(id: Int): WishEntity? = dao.getById(id)

    suspend fun addWish(wish: WishEntity) = dao.insert(wish)

    suspend fun updateWish(wish: WishEntity) = dao.update(wish)

    suspend fun deleteWish(wish: WishEntity) = dao.delete(wish)
}