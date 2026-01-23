package com.hathway.ramadankareem2026.ui.zakat.domain.model

/**
 * Represents the result of a Zakat calculation
 *
 * @param zakatAmount Calculated zakat (2.5%)
 * @param isEligible Whether zakat is wajib
 * @param nisabType Selected Nisab type (Gold / Silver)
 */

data class ZakatResult(
    val assets: ZakatAssets,
    val nisabValue: Double,
    val zakatAmount: Double,
    val isEligible: Boolean,
    val nisabType: NisabType
)
