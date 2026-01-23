package com.hathway.ramadankareem2026.ui.zakat.domain.usecase

import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType
import com.hathway.ramadankareem2026.ui.zakat.domain.model.ZakatAssets
import com.hathway.ramadankareem2026.ui.zakat.domain.model.ZakatResult

/**
 * Business logic for Zakat calculation
 *
 * Nisab:
 * - Gold: 85g
 * - Silver: 595g
 *
 * NOTE: Prices are placeholders and can be injected later.
 */

class CalculateZakatUseCase {

    operator fun invoke(
        assets: ZakatAssets,
        nisabType: NisabType
    ): ZakatResult {

        val nisab = when (nisabType) {
            NisabType.GOLD -> 85 * 450.0     // example gold price
            NisabType.SILVER -> 595 * 6.0   // example silver price
        }

        val eligible = assets.netWorth >= nisab
        val zakat = if (eligible) assets.netWorth * 0.025 else 0.0

        return ZakatResult(
            assets = assets,
            nisabValue = nisab,
            zakatAmount = zakat,
            isEligible = eligible,
            nisabType = nisabType
        )
    }
}
