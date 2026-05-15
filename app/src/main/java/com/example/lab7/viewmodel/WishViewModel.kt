package com.example.lab7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab7.data.WishRepository
import com.example.lab7.model.WishEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishViewModel @Inject constructor(
    private val repository: WishRepository
) : ViewModel() {

    val allWishes: StateFlow<List<WishEntity>> =
        repository.getAllWishes().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedWish = MutableStateFlow<WishEntity?>(null)
    val selectedWish: StateFlow<WishEntity?> = _selectedWish.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadWish(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _selectedWish.value = repository.getWishById(id)
            } catch (e: Exception) {
                _error.value = e.message ?: "Неизвестная ошибка"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addWish(wish: WishEntity) {
        viewModelScope.launch {
            repository.addWish(wish)
        }
    }

    fun updateWish(wish: WishEntity) {
        viewModelScope.launch {
            repository.updateWish(wish)
        }
    }

    fun deleteWish(wish: WishEntity) {
        viewModelScope.launch {
            repository.deleteWish(wish)
        }
    }
}