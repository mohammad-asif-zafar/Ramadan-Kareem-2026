package com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
import com.hathway.ramadankareem2026.ui.tips.data.service.RandomRamadanTipsService
import com.hathway.ramadankareem2026.ui.tips.data.repository.TipsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing random Ramadan tips with clean MVVM architecture.
 * Provides reactive state management and multi-language support.
 */
class RandomRamadanTipsViewModel(
    private val randomTipsService: RandomRamadanTipsService,
    private val repository: TipsRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RandomRamadanTipsUiState())
    val uiState: StateFlow<RandomRamadanTipsUiState> = _uiState.asStateFlow()
    
    private val _currentTip = MutableStateFlow<Tip?>(null)
    val currentTip: StateFlow<Tip?> = _currentTip.asStateFlow()
    
    init {
        loadInitialRandomTip()
        observeRandomTips()
    }
    
    private fun loadInitialRandomTip() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val randomTip = randomTipsService.getRandomRamadanTip()
                _currentTip.value = randomTip
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load random tip: ${e.message}"
                )
            }
        }
    }
    
    private fun observeRandomTips() {
        viewModelScope.launch {
            randomTipsService.currentRandomTip.collect { tip ->
                _currentTip.value = tip
            }
        }
    }
    
    /**
     * Loads a new random Ramadan tip.
     */
    fun loadNewRandomTip() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val randomTip = randomTipsService.getRandomRamadanTip()
                _currentTip.value = randomTip
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load new tip: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Loads a random tip from a specific category.
     */
    fun loadRandomTipFromCategory(category: TipCategory) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val randomTip = randomTipsService.getRandomTipFromCategory(category)
                _currentTip.value = randomTip
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load tip from ${category.displayName}: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Loads a random daily Ramadan tip.
     */
    fun loadRandomDailyTip() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val randomTip = randomTipsService.getRandomDailyRamadanTip()
                _currentTip.value = randomTip
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load daily tip: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Gets multiple random tips for variety.
     */
    fun loadMultipleRandomTips(count: Int = 3) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val randomTips = randomTipsService.getRandomTips(count)
                _uiState.value = _uiState.value.copy(
                    multipleTips = randomTips,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load multiple tips: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Clears any error state.
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    /**
     * Resets the tips tracking for fresh randomization.
     */
    fun resetTips() {
        randomTipsService.resetUsedTips()
        loadNewRandomTip()
    }
}

/**
 * UI state for random Ramadan tips screen.
 */
data class RandomRamadanTipsUiState(
    val isLoading: Boolean = false,
    val multipleTips: List<Tip> = emptyList(),
    val error: String? = null
)

/**
 * Factory for creating RandomRamadanTipsViewModel instances.
 */
class RandomRamadanTipsViewModelFactory(
    private val randomTipsService: RandomRamadanTipsService = RandomRamadanTipsService(),
    private val repository: TipsRepository = TipsRepository()
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomRamadanTipsViewModel::class.java)) {
            return RandomRamadanTipsViewModel(randomTipsService, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
