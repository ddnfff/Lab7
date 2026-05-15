package com.example.lab7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab7.data.CarServiceRepository
import com.example.lab7.model.ServiceRecordEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val repository: CarServiceRepository
) : ViewModel() {

    val allRecords: StateFlow<List<ServiceRecordEntity>> =
        repository.getAllRecords().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedRecord = MutableStateFlow<ServiceRecordEntity?>(null)
    val selectedRecord: StateFlow<ServiceRecordEntity?> = _selectedRecord.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadRecord(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _selectedRecord.value = repository.getRecordById(id)
            } catch (e: Exception) {
                _error.value = e.message ?: "Неизвестная ошибка"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addRecord(record: ServiceRecordEntity) {
        viewModelScope.launch {
            repository.addRecord(record)
        }
    }

    fun updateRecord(record: ServiceRecordEntity) {
        viewModelScope.launch {
            repository.updateRecord(record)
        }
    }

    fun deleteRecord(record: ServiceRecordEntity) {
        viewModelScope.launch {
            repository.deleteRecord(record)
        }
    }
}