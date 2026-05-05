package com.example.lab7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab7.data.PersonRepository
import com.example.lab7.model.PersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.lab7.model.TodoDto


@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: PersonRepository
) : ViewModel() {

    val allPersons: Flow<List<PersonEntity>> = repository.getAllPersons()

    private val _isListLoading = MutableStateFlow(false)
    val isListLoading: StateFlow<Boolean> = _isListLoading.asStateFlow()

    private val _listError = MutableStateFlow<String?>(null)
    val listError: StateFlow<String?> = _listError.asStateFlow()

    private val _isDetailLoading = MutableStateFlow(false)
    val isDetailLoading: StateFlow<Boolean> = _isDetailLoading.asStateFlow()

    private val _detailError = MutableStateFlow<String?>(null)
    val detailError: StateFlow<String?> = _detailError.asStateFlow()

    fun refreshData() = viewModelScope.launch {
        _isListLoading.value = true
        _listError.value = null
        try {
            repository.refreshPersons()
        } catch (e: Exception) {
            _listError.value = e.message ?: "Ошибка загрузки данных"
        } finally {
            _isListLoading.value = false
        }
    }

    private val _userTodos = MutableStateFlow<List<TodoDto>>(emptyList())
    val userTodos: StateFlow<List<TodoDto>> = _userTodos.asStateFlow()

    private val _isTodosLoading = MutableStateFlow(false)
    val isTodosLoading: StateFlow<Boolean> = _isTodosLoading.asStateFlow()

    private val _todosError = MutableStateFlow<String?>(null)
    val todosError: StateFlow<String?> = _todosError.asStateFlow()

    fun loadTodosByUserId(userId: Int) = viewModelScope.launch {
        _isTodosLoading.value = true
        _todosError.value = null
        try {
            _userTodos.value = repository.getTodosByUserId(userId)
        } catch (e: Exception) {
            _todosError.value = e.message ?: "Ошибка загрузки задач"
        } finally {
            _isTodosLoading.value = false
        }
    }

    suspend fun getPerson(id: Int): PersonEntity? {
        _isDetailLoading.value = true
        _detailError.value = null
        return try {
            repository.getPerson(id)
        } catch (e: Exception) {
            _detailError.value = e.message ?: "Ошибка загрузки данных"
            null
        } finally {
            _isDetailLoading.value = false
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