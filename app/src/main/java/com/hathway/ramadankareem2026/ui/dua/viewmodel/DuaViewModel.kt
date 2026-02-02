package com.hathway.ramadankareem2026.ui.dua.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

class DuaViewModel(
    private val repository: DuaRepository = DuaRepository()
) : ViewModel() {

    // Ramadan section (horizontal)
    val ramadanDuas = repository.getRamadanDuas()

}
