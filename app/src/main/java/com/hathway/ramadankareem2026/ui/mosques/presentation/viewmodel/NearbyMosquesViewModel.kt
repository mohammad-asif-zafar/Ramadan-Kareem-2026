package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import androidx.compose.runtime.State

class NearbyMosquesViewModel(
    private val useCase: GetNearbyMosquesUseCase = GetNearbyMosquesUseCase()
) : ViewModel() {

    // 🔒 Internal mutable state
    private val _mosques = mutableStateOf<List<Mosque>>(emptyList())

    // 👀 Public read-only state (UI observes this)
    val mosques: State<List<Mosque>> = _mosques

    fun load(userLat: Double, userLng: Double) {
        _mosques.value = useCase(userLat, userLng)
    }
}

