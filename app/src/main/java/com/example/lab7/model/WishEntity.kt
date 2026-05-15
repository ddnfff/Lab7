package com.example.lab7.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishes")
data class WishEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val price: Double,
    val url: String,
    val priority: String,
    val isPurchased: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)