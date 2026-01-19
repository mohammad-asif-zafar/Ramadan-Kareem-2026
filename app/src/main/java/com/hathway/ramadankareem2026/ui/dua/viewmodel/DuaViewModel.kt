package com.hathway.ramadankareem2026.ui.dua.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

class DuaViewModel(
    private val repository: DuaRepository = DuaRepository()
) : ViewModel() {

    // Ramadan section (horizontal)
    val ramadanDuas = repository.getRamadanDuas()

    // Categories (2x2 grid)
    val categories = repository.getCategories()

    fun getDuasByCategory(categoryId: String): List<DuaItem> {
        return repository.getDuasByCategory(categoryId)
    }
}
