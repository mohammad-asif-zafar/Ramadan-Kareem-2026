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

class NavViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OnboardingRepository(application)
    
    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)
    val isOnboardingCompleted: StateFlow<Boolean?> = _isOnboardingCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            _isOnboardingCompleted.value = repository.isOnboardingCompleted.first()
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            repository.setOnboardingCompleted()
            _isOnboardingCompleted.value = true
        }
    }
}
