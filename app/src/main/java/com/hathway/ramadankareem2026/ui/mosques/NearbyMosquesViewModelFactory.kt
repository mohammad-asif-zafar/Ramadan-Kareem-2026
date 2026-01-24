package com.hathway.ramadankareem2026.ui.mosques

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.NearbyMosquesViewModel

class NearbyMosquesViewModelFactory(
    private val useCase: GetNearbyMosquesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearbyMosquesViewModel::class.java)) {
           // return NearbyMosquesViewModel(useCase) as T
        }
        error("Unknown ViewModel")
    }
}
