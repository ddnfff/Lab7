package com.example.lab7.data

import com.example.lab7.data.PersonApi
import com.example.lab7.data.PersonDao
import com.example.lab7.model.PersonEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val api: PersonApi,
    private val dao: PersonDao
) {
    fun getAllPersons(): Flow<List<PersonEntity>> = dao.getAll()

    suspend fun refreshPersons() {
        val response = api.getAllPersons()
        if (response.isSuccessful) {
            response.body()?.let { persons ->
                dao.insertAll(persons.map { it.toEntity() })
            }
        }
    }

    suspend fun getPerson(id: Int): PersonEntity {
        return dao.getById(id) ?: fetchFromNetwork(id)
    }

    suspend fun insert(person: PersonEntity) = dao.insert(person)

    suspend fun update(person: PersonEntity) = dao.insert(person) // Используем insert для обновления

    suspend fun delete(id: Int) = dao.delete(id)

    suspend fun insertAll(persons: List<PersonEntity>) = dao.insertAll(persons)

    private suspend fun fetchFromNetwork(id: Int): PersonEntity {
        val response = api.getPersonById(id)
        if (response.isSuccessful) {
            return response.body()!!.toEntity().also { dao.insert(it) }
        }
        throw Exception("Network error: ${response.code()}")
    }
}