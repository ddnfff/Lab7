package com.example.lab7.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_records")
data class ServiceRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val serviceDate: String,
    val mileage: Int,
    val serviceType: String,
    val cost: Double,
    val location: String,
    val notes: String,
    val createdAt: Long = System.currentTimeMillis()
)