package com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.ramadankareem2026.ui.zakat.domain.model.*
import com.hathway.ramadankareem2026.ui.zakat.domain.usecase.CalculateZakatUseCase
import com.hathway.ramadankareem2026.ui.zakat.presentation.ZakatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ZakatViewModel : ViewModel() {

    private val useCase = CalculateZakatUseCase()

    private val _uiState = MutableStateFlow(ZakatUiState())
    val uiState: StateFlow<ZakatUiState> = _uiState

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

    private fun update(block: ZakatUiState.() -> ZakatUiState) {
        _uiState.value = _uiState.value.block()
    }
}




