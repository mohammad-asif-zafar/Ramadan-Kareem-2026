package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.presentation.state.MosqueUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MosqueViewModel(
    private val getNearbyMosques: GetNearbyMosquesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MosqueUiState())
    val uiState: StateFlow<MosqueUiState> = _uiState

    fun load(lat: Double, lng: Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            runCatching {
                getNearbyMosques(lat, lng)
            }.onSuccess {
                _uiState.value = MosqueUiState(mosques = it)
            }.onFailure {
                _uiState.value = MosqueUiState(error = it.message)
            }
        }
    }
}

