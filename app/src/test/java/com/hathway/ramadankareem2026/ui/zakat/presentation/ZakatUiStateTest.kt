package com.hathway.ramadankareem2026.ui.zakat.presentation

import com.hathway.ramadankareem2026.core.currency.CountryCurrencyMapper
import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType
import org.junit.Test
import org.junit.Assert.*

class ZakatUiStateTest {

    @Test
    fun `default state should have USD currency`() {
        val state = ZakatUiState()
        assertEquals(CountryCurrencyMapper.defaultCurrency, state.currency)
        assertEquals("", state.country)
        assertFalse(state.isLoadingCurrency)
        assertNull(state.currencyError)
    }

    @Test
    fun `state should hold Saudi currency correctly`() {
        val saudiCurrency = CountryCurrencyMapper.getCurrencyForCountry("saudi arabia")
        val state = ZakatUiState(currency = saudiCurrency, country = "Saudi Arabia")
        assertEquals("SAR", state.currency.code)
        assertEquals("﷼", state.currency.symbol)
    }

    @Test
    fun `state should handle loading and error states`() {
        val loadingState = ZakatUiState(isLoadingCurrency = true)
        assertTrue(loadingState.isLoadingCurrency)
        
        val errorState = ZakatUiState(currencyError = "Permission denied")
        assertEquals("Permission denied", errorState.currencyError)
    }

    @Test
    fun `state should hold input values correctly`() {
        val state = ZakatUiState(
            gold = "10000", silver = "5000", cash = "20000", 
            debts = "3000", selectedNisab = NisabType.SILVER
        )
        assertEquals("10000", state.gold)
        assertEquals(NisabType.SILVER, state.selectedNisab)
    }

    @Test
    fun `state should handle zero decimal currencies`() {
        val indonesiaCurrency = CountryCurrencyMapper.getCurrencyForCountry("indonesia")
        val state = ZakatUiState(currency = indonesiaCurrency, country = "Indonesia")
        assertEquals(0, state.currency.decimalPlaces)
        assertEquals("Rp", state.currency.symbol)
    }
}
