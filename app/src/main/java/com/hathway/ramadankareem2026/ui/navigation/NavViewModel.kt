package com.hathway.ramadankareem2026.ui.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.onboarding.OnboardingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class NavViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OnboardingRepository(application)
    
    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)
    val isOnboardingCompleted: StateFlow<Boolean?> = _isOnboardingCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                @Suppress("DEPRECATION")
                val completed = withTimeoutOrNull(1500L) {
                    repository.isOnboardingCompleted.first()
                } ?: false
                _isOnboardingCompleted.value = completed
            } catch (_: Exception) {
                _isOnboardingCompleted.value = false
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            repository.setOnboardingCompleted()
            _isOnboardingCompleted.value = true
        }
    }
}
