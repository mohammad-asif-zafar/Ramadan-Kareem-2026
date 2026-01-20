package com.hathway.ramadankareem2026.ui.allahnames.viewmodel


import androidx.lifecycle.ViewModel
import com.hathway.ramadankareem2026.ui.allahnames.data.repository.AllahNamesRepository
import com.hathway.ramadankareem2026.ui.allahnames.domain.usecase.GetAllahNamesUseCase


class AllahNamesViewModel(
    private val repository: AllahNamesRepository = AllahNamesRepository()
) : ViewModel() {

    private val getAllahNamesUseCase = GetAllahNamesUseCase(repository)

    val names = getAllahNamesUseCase()
}

