package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MosqueViewModel(
    private val getNearbyMosques: GetNearbyMosquesUseCase
) : ViewModel() {

    private val _mosques = MutableStateFlow<List<Mosque>>(emptyList())
    val mosques: StateFlow<List<Mosque>> = _mosques

    fun loadNearby(lat: Double, lng: Double) {
        viewModelScope.launch {
            _mosques.value = getNearbyMosques(lat, lng)
        }
    }
}
