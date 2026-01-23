package com.hathway.ramadankareem2026.ui.zakat.domain.model

data class ZakatAssets(
    val gold: Double,
    val silver: Double,
    val cash: Double,
    val debts: Double
) {
    val netWorth: Double
        get() = gold + silver + cash - debts
}
