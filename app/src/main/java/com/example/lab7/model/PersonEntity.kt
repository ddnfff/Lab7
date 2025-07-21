package com.example.lab7.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val age: Int,
    val profession: String,
    val lastUpdated: Long = System.currentTimeMillis()
)