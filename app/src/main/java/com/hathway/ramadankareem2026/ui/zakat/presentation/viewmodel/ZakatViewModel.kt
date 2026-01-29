package com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.currency.CountryCurrencyMapper
import com.hathway.ramadankareem2026.core.currency.CurrencyResult
import com.hathway.ramadankareem2026.core.currency.LocationCurrencyService
import com.hathway.ramadankareem2026.ui.zakat.domain.model.*
import com.hathway.ramadankareem2026.ui.zakat.domain.usecase.CalculateZakatUseCase
import com.hathway.ramadankareem2026.ui.zakat.presentation.ZakatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ZakatViewModel(
    private val currencyService: LocationCurrencyService? = null
) : ViewModel() {

    private val useCase = CalculateZakatUseCase()

    private val _uiState = MutableStateFlow(ZakatUiState())
    val uiState: StateFlow<ZakatUiState> = _uiState

    init {
        loadCurrencyFromLocation()
    }

    fun onGold(v: String) = update { copy(gold = v) }
    fun onSilver(v: String) = update { copy(silver = v) }
    fun onCash(v: String) = update { copy(cash = v) }
    fun onDebts(v: String) = update { copy(debts = v) }
    fun onNisab(type: NisabType) = update { copy(selectedNisab = type) }

    fun calculate() {
        val assets = ZakatAssets(
            gold = _uiState.value.gold.toDoubleOrNull() ?: 0.0,
            silver = _uiState.value.silver.toDoubleOrNull() ?: 0.0,
            cash = _uiState.value.cash.toDoubleOrNull() ?: 0.0,
            debts = _uiState.value.debts.toDoubleOrNull() ?: 0.0
        )

        val result = useCase(assets, _uiState.value.selectedNisab)
        update { copy(result = result) }
    }

    /**
     * Load currency based on device location
     */
    fun loadCurrencyFromLocation() {
        currencyService?.let { service ->
            viewModelScope.launch {
                update { copy(isLoadingCurrency = true, currencyError = null) }
                
                try {
                    val currencyResult = service.getCurrentCurrency()
                    when (currencyResult) {
                        is CurrencyResult.Success -> {
                            update { 
                                copy(
                                    currency = currencyResult.currency,
                                    country = currencyResult.country,
                                    isLoadingCurrency = false,
                                    currencyError = null
                                ) 
                            }
                        }
                        is CurrencyResult.Error -> {
                            update { 
                                copy(
                                    isLoadingCurrency = false,
                                    currencyError = currencyResult.message
                                ) 
                            }
                        }
                    }
                } catch (e: Exception) {
                    update { 
                        copy(
                            isLoadingCurrency = false,
                            currencyError = "Failed to load currency: ${e.message}"
                        ) 
                    }
                }
            }
        }
    }

    /**
     * Manually set currency for a specific country
     */
    fun setCurrencyForCountry(countryName: String) {
        currencyService?.let { service ->
            viewModelScope.launch {
                val currencyResult = service.getCurrencyForCountry(countryName)
                when (currencyResult) {
                    is CurrencyResult.Success -> {
                        update { 
                            copy(
                                currency = currencyResult.currency,
                                country = currencyResult.country,
                                currencyError = null
                            ) 
                        }
                    }
                    is CurrencyResult.Error -> {
                        update { 
                            copy(
                                currencyError = currencyResult.message
                            ) 
                        }
                    }
                }
            }
        }
    }

    private fun update(block: ZakatUiState.() -> ZakatUiState) {
        _uiState.value = _uiState.value.block()
    }
}




