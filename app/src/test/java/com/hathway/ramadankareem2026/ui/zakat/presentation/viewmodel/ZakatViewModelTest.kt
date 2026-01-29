package com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hathway.ramadankareem2026.core.currency.CountryCurrencyMapper
import com.hathway.ramadankareem2026.core.currency.CurrencyResult
import com.hathway.ramadankareem2026.core.currency.LocationCurrencyService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class ZakatViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockCurrencyService: LocationCurrencyService
    private lateinit var viewModel: ZakatViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockCurrencyService = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have default currency`() = runTest {
        coEvery { mockCurrencyService.getCurrentCurrency() } returns CurrencyResult.Success(
            currency = CountryCurrencyMapper.defaultCurrency, country = "Unknown", city = "Unknown", source = "Default"
        )

        viewModel = ZakatViewModel(mockCurrencyService)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(CountryCurrencyMapper.defaultCurrency, state.currency)
        assertEquals("Unknown", state.country)
        assertFalse(state.isLoadingCurrency)
        assertNull(state.currencyError)
    }

    @Test
    fun `should load Saudi currency from location`() = runTest {
        val saudiCurrency = CountryCurrencyMapper.getCurrencyForCountry("saudi arabia")
        coEvery { mockCurrencyService.getCurrentCurrency() } returns CurrencyResult.Success(
            currency = saudiCurrency, country = "Saudi Arabia", city = "Riyadh", source = "Location"
        )

        viewModel = ZakatViewModel(mockCurrencyService)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(saudiCurrency, state.currency)
        assertEquals("Saudi Arabia", state.country)
        coVerify { mockCurrencyService.getCurrentCurrency() }
    }

    @Test
    fun `should handle currency loading error`() = runTest {
        coEvery { mockCurrencyService.getCurrentCurrency() } returns CurrencyResult.Error("Permission denied")

        viewModel = ZakatViewModel(mockCurrencyService)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Permission denied", state.currencyError)
        assertFalse(state.isLoadingCurrency)
    }

    @Test
    fun `setCurrencyForCountry should update currency`() = runTest {
        viewModel = ZakatViewModel(mockCurrencyService)
        testDispatcher.scheduler.advanceUntilIdle()

        val uaeCurrency = CountryCurrencyMapper.getCurrencyForCountry("united arab emirates")
        coEvery { mockCurrencyService.getCurrencyForCountry("UAE") } returns CurrencyResult.Success(
            currency = uaeCurrency, country = "UAE", city = "Dubai", source = "Manual"
        )

        viewModel.setCurrencyForCountry("UAE")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(uaeCurrency, state.currency)
        assertEquals("UAE", state.country)
        coVerify { mockCurrencyService.getCurrencyForCountry("UAE") }
    }

    @Test
    fun `zakat calculation should work with different currencies`() = runTest {
        val turkeyCurrency = CountryCurrencyMapper.getCurrencyForCountry("turkey")
        coEvery { mockCurrencyService.getCurrentCurrency() } returns CurrencyResult.Success(
            currency = turkeyCurrency, country = "Turkey", city = "Istanbul", source = "Location"
        )

        viewModel = ZakatViewModel(mockCurrencyService)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onGold("10000")
        viewModel.onSilver("5000")
        viewModel.onCash("20000")
        viewModel.onDebts("3000")
        viewModel.calculate()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(turkeyCurrency, state.currency)
        assertNotNull(state.result)
        assertEquals("₺", state.currency.symbol)
    }

    @Test
    fun `loadCurrencyFromLocation should set loading state`() = runTest {
        viewModel = ZakatViewModel(mockCurrencyService)
        
        // Mock delay to test loading state
        coEvery { mockCurrencyService.getCurrentCurrency() } coAnswers {
            kotlinx.coroutines.delay(100)
            CurrencyResult.Success(
                currency = CountryCurrencyMapper.defaultCurrency, country = "USA", city = "New York", source = "Location"
            )
        }

        viewModel.loadCurrencyFromLocation()
        
        var state = viewModel.uiState.value
        assertTrue(state.isLoadingCurrency)

        testDispatcher.scheduler.advanceUntilIdle()
        
        state = viewModel.uiState.value
        assertFalse(state.isLoadingCurrency)
        assertEquals("USA", state.country)
    }
}
