package com.example.lab7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab7.data.PersonRepository
import com.example.lab7.model.PersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: PersonRepository
) : ViewModel() {
    val allPersons: Flow<List<PersonEntity>> = repository.getAllPersons()

    fun refreshData() = viewModelScope.launch {
        repository.refreshPersons()
    }

    suspend fun getPerson(id: Int): PersonEntity? {
        return try {
            repository.getPerson(id)
        } catch (e: Exception) {
            null
        }
    }

    fun insertPerson(person: PersonEntity) = viewModelScope.launch {
        repository.insert(person)
    }

    fun updatePerson(person: PersonEntity) = viewModelScope.launch {
        repository.update(person)
    }

    fun deletePerson(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }
}

