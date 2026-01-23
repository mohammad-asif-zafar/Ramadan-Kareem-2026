package com.hathway.ramadankareem2026.ui.zakat.presentation

import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType
import com.hathway.ramadankareem2026.ui.zakat.domain.model.ZakatResult

/**
 * UI State for Zakat screen
 */

data class ZakatUiState(
    val gold: String = "",
    val silver: String = "",
    val cash: String = "",
    val debts: String = "",
    val selectedNisab: NisabType = NisabType.GOLD,
    val result: ZakatResult? = null
)



