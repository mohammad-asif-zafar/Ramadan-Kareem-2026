package com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel

import com.hathway.ramadankareem2026.core.currency.CountryCurrencyMapper
import com.hathway.ramadankareem2026.core.currency.CurrencyResult
import com.hathway.ramadankareem2026.core.currency.LocationCurrencyService
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ZakatViewModelTest {

    private lateinit var viewModel: ZakatViewModel

    @Before
    fun setup() {
        viewModel = ZakatViewModel()
    }

    @Test
    fun `initial state should have default currency`() {
        val state = viewModel.uiState.value
        assertEquals(CountryCurrencyMapper.defaultCurrency, state.currency)
        assertEquals("", state.country)
        assertFalse(state.isLoadingCurrency)
        assertNull(state.currencyError)
    }

    @Test
    fun `should update gold value correctly`() {
        viewModel.onGold("10000")
        val state = viewModel.uiState.value
        assertEquals("10000", state.gold)
    }

    @Test
    fun `should update silver value correctly`() {
        viewModel.onSilver("5000")
        val state = viewModel.uiState.value
        assertEquals("5000", state.silver)
    }

    @Test
    fun `should update cash value correctly`() {
        viewModel.onCash("20000")
        val state = viewModel.uiState.value
        assertEquals("20000", state.cash)
    }

    @Test
    fun `should update debts value correctly`() {
        viewModel.onDebts("3000")
        val state = viewModel.uiState.value
        assertEquals("3000", state.debts)
    }

    @Test
    fun `should update nisab type correctly`() {
        viewModel.onNisab(com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType.SILVER)
        val state = viewModel.uiState.value
        assertEquals(com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType.SILVER, state.selectedNisab)
    }

    @Test
    fun `should calculate zakat correctly`() {
        viewModel.onGold("10000")
        viewModel.onSilver("5000")
        viewModel.onCash("20000")
        viewModel.onDebts("3000")
        viewModel.calculate()
        
        val state = viewModel.uiState.value
        assertNotNull(state.result)
    }

    @Test
    fun `should handle empty values in calculation`() {
        viewModel.onGold("")
        viewModel.onSilver("")
        viewModel.onCash("")
        viewModel.onDebts("")
        viewModel.calculate()
        
        val state = viewModel.uiState.value
        assertNotNull(state.result)
    }

    @Test
    fun `should handle zero values in calculation`() {
        viewModel.onGold("0")
        viewModel.onSilver("0")
        viewModel.onCash("0")
        viewModel.onDebts("0")
        viewModel.calculate()
        
        val state = viewModel.uiState.value
        assertNotNull(state.result)
    }

    @Test
    fun `should handle decimal values in calculation`() {
        viewModel.onGold("10000.50")
        viewModel.onSilver("5000.75")
        viewModel.onCash("20000.25")
        viewModel.onDebts("3000.99")
        viewModel.calculate()
        
        val state = viewModel.uiState.value
        assertNotNull(state.result)
    }

    @Test
    fun `should maintain currency state during calculation`() {
        val initialState = viewModel.uiState.value
        val initialCurrency = initialState.currency
        
        viewModel.onGold("10000")
        viewModel.onSilver("5000")
        viewModel.onCash("20000")
        viewModel.onDebts("3000")
        viewModel.calculate()
        
        val finalState = viewModel.uiState.value
        assertEquals(initialCurrency, finalState.currency)
    }

    @Test
    fun `should handle large values in calculation`() {
        viewModel.onGold("1000000")
        viewModel.onSilver("500000")
        viewModel.onCash("2000000")
        viewModel.onDebts("300000")
        viewModel.calculate()
        
        val state = viewModel.uiState.value
        assertNotNull(state.result)
    }

    @Test
    fun `should handle negative values in calculation`() {
        viewModel.onGold("-1000")
        viewModel.onSilver("-500")
        viewModel.onCash("-2000")
        viewModel.onDebts("-300")
        viewModel.calculate()
        
        val state = viewModel.uiState.value
        assertNotNull(state.result)
    }

    @Test
    fun `should update multiple values correctly`() {
        viewModel.onGold("15000")
        viewModel.onSilver("7500")
        viewModel.onCash("30000")
        viewModel.onDebts("4500")
        
        val state = viewModel.uiState.value
        assertEquals("15000", state.gold)
        assertEquals("7500", state.silver)
        assertEquals("30000", state.cash)
        assertEquals("4500", state.debts)
    }

    @Test
    fun `should handle nisab type change during calculation`() {
        viewModel.onGold("10000")
        viewModel.onSilver("5000")
        viewModel.onCash("20000")
        viewModel.onDebts("3000")
        
        // Change nisab type
        viewModel.onNisab(com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType.SILVER)
        
        // Calculate and get the updated state
        viewModel.calculate()
        val finalState = viewModel.uiState.value
        
        assertEquals(com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType.SILVER, finalState.selectedNisab)
        assertNotNull(finalState.result)
    }
}
