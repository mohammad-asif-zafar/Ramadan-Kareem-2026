package com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
import com.hathway.ramadankareem2026.ui.tips.data.repository.TipsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TipsViewModel(
    private val repository: TipsRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TipsUiState())
    val uiState: StateFlow<TipsUiState> = _uiState.asStateFlow()
    
    private val _dailyTip = MutableStateFlow(repository.getDailyTip())
    val dailyTip: StateFlow<Tip> = _dailyTip.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<TipCategory?>(null)
    val selectedCategory: StateFlow<TipCategory?> = _selectedCategory.asStateFlow()
    
    init {
        loadAllTips()
        loadDailyTip()
    }
    
    private fun loadAllTips() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val allTips = repository.getAllTips()
                val categories = allTips.map { it.category }.distinct().sortedBy { it.priority }
                
                _uiState.value = _uiState.value.copy(
                    allTips = allTips,
                    categories = categories,
                    filteredTips = allTips,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load tips: ${e.message}"
                )
            }
        }
    }
    
    private fun loadDailyTip() {
        viewModelScope.launch {
            try {
                _dailyTip.value = repository.getDailyTip()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load daily tip: ${e.message}"
                )
            }
        }
    }
    
    fun selectCategory(category: TipCategory?) {
        _selectedCategory.value = category
        filterTips(category)
    }
    
    private fun filterTips(category: TipCategory?) {
        val filtered = if (category == null) {
            _uiState.value.allTips
        } else {
            _uiState.value.allTips.filter { it.category == category }
        }
        _uiState.value = _uiState.value.copy(filteredTips = filtered)
    }

    fun getTipById(id: Int): Tip? {
        return repository.getTipById(id)
    }

}

data class TipsUiState(
    val isLoading: Boolean = false,
    val allTips: List<Tip> = emptyList(),
    val filteredTips: List<Tip> = emptyList(),
    val categories: List<TipCategory> = emptyList(),
    val error: String? = null
)

class TipsViewModelFactory(
    private val repository: TipsRepository = TipsRepository()
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipsViewModel::class.java)) {
            return TipsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
