package com.hathway.ramadankareem2026.ui.zakat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.database.entity.ZakatCalculationEntity
import com.hathway.ramadankareem2026.data.repository.ZakatCalculationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ZakatCalculationViewModel(
    private val repository: ZakatCalculationRepository
) : ViewModel() {
    
    private val _calculations = MutableStateFlow<List<ZakatCalculationEntity>>(emptyList())
    val calculations: StateFlow<List<ZakatCalculationEntity>> = _calculations.asStateFlow()
    
    private val _calculationsCount = MutableStateFlow(0)
    val calculationsCount: StateFlow<Int> = _calculationsCount.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadCalculations()
        loadCalculationsCount()
    }
    
    fun saveCalculation(
        goldValue: Double,
        silverValue: Double,
        cash: Double,
        debts: Double,
        nisabType: String,
        totalAssets: Double,
        totalLiabilities: Double,
        zakatPayable: Double
    ) {
        viewModelScope.launch {
            try {
                println("DEBUG: Starting save calculation")
                _isLoading.value = true
                val result = repository.saveCalculation(
                    goldValue = goldValue,
                    silverValue = silverValue,
                    cash = cash,
                    debts = debts,
                    nisabType = nisabType,
                    totalAssets = totalAssets,
                    totalLiabilities = totalLiabilities,
                    zakatPayable = zakatPayable
                )
                println("DEBUG: Save calculation result: $result")
                loadCalculations()
                loadCalculationsCount()
                _errorMessage.value = null
                println("DEBUG: Save calculation completed successfully")
            } catch (e: Exception) {
                println("DEBUG: Error saving calculation: ${e.message}")
                _errorMessage.value = "Failed to save calculation: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun loadCalculations() {
        viewModelScope.launch {
            try {
                println("DEBUG: Loading calculations from repository")
                repository.getAllCalculations().collect { calculations ->
                    println("DEBUG: Received ${calculations.size} calculations from database")
                    _calculations.value = calculations
                }
            } catch (e: Exception) {
                println("DEBUG: Error loading calculations: ${e.message}")
                _errorMessage.value = "Failed to load calculations: ${e.message}"
            }
        }
    }
    
    private fun loadCalculationsCount() {
        viewModelScope.launch {
            try {
                println("DEBUG: Loading calculations count from repository")
                repository.getCalculationsCount().collect { count ->
                    println("DEBUG: Received calculations count: $count")
                    _calculationsCount.value = count
                }
            } catch (e: Exception) {
                println("DEBUG: Error loading calculations count: ${e.message}")
                _errorMessage.value = "Failed to load calculations count: ${e.message}"
            }
        }
    }
    
    fun deleteCalculation(calculation: ZakatCalculationEntity) {
        viewModelScope.launch {
            try {
                repository.deleteCalculation(calculation)
                loadCalculations()
                loadCalculationsCount()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete calculation: ${e.message}"
            }
        }
    }
    
    fun deleteAllCalculations() {
        viewModelScope.launch {
            try {
                repository.deleteAllCalculations()
                loadCalculations()
                loadCalculationsCount()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete all calculations: ${e.message}"
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
