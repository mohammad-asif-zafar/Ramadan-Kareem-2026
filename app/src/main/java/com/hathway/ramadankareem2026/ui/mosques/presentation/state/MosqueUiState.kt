package com.hathway.ramadankareem2026.ui.mosques.presentation.state

import com.hathway.ramadankareem2026.core.location.LocationUiState
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque

data class MosqueUiState(
    val isLoading: Boolean = false,
    val mosques: List<Mosque> = emptyList(),
    val selectedMosque: Mosque? = null,
    val userLocation: LocationUiState.Success? = null,
    val error: String? = null
)


