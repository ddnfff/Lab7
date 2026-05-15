package com.example.lab7.data

import com.example.lab7.model.ServiceRecordEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarServiceRepository @Inject constructor(
    private val dao: ServiceRecordDao
) {
    fun getAllRecords(): Flow<List<ServiceRecordEntity>> = dao.getAll()

    suspend fun getRecordById(id: Int): ServiceRecordEntity? = dao.getById(id)

    suspend fun addRecord(record: ServiceRecordEntity) = dao.insert(record)

    suspend fun updateRecord(record: ServiceRecordEntity) = dao.update(record)

    suspend fun deleteRecord(record: ServiceRecordEntity) = dao.delete(record)
}