package com.hathway.ramadankareem2026.core.location


/**
 * Sealed UI state for Location.
 * Forces exhaustive handling in UI.
 */
sealed interface LocationUiState {

    /** While fetching or upgrading DEMO → REAL */
    data object Loading : LocationUiState

    /** When a valid location exists (DEMO / GPS / NETWORK) */
    data class Success(
        val city: String,
        val country: String,
        val latitude: Double,
        val longitude: Double,
        val source: LocationSource,
        val selectionMode: LocationSelectionMode
    ) : LocationUiState

    /** When location failed (permission denied, etc.) */
    data class Error(
        val message: String
    ) : LocationUiState
}
