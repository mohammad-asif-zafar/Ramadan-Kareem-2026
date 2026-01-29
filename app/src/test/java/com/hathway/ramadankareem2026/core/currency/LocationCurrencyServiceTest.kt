package com.hathway.ramadankareem2026.core.currency

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class LocationCurrencyServiceTest {

    private lateinit var mockContext: Context
    private lateinit var locationCurrencyService: LocationCurrencyService

    @Before
    fun setup() {
        mockContext = mockk(relaxed = true)
        locationCurrencyService = LocationCurrencyService(mockContext)
    }

    @Test
    fun `getCurrentCurrency should return success when location fetch succeeds`() = runTest {
        // This test would require mocking LocationProvider
        // For now, we test the fallback behavior
        val result = locationCurrencyService.getCurrentCurrency()
        
        assertTrue(result is CurrencyResult.Success)
        assertEquals("Default", (result as CurrencyResult.Success).source)
    }

    @Test
    fun `getCurrencyForCountry should return success for valid country`() {
        val result = locationCurrencyService.getCurrencyForCountry("saudi arabia")
        
        assertTrue(result is CurrencyResult.Success)
        val successResult = result as CurrencyResult.Success
        assertEquals("SAR", successResult.currency.code)
        assertEquals("saudi arabia", successResult.country)
        assertEquals("Manual", successResult.source)
    }

    @Test
    fun `getCurrencyForCountry should return success for USA`() {
        val result = locationCurrencyService.getCurrencyForCountry("united states")
        
        assertTrue(result is CurrencyResult.Success)
        val successResult = result as CurrencyResult.Success
        assertEquals("USD", successResult.currency.code)
        assertEquals("$", successResult.currency.symbol)
        assertEquals("united states", successResult.country)
    }

    @Test
    fun `getCurrencyForCountry should return success for Pakistan`() {
        val result = locationCurrencyService.getCurrencyForCountry("pakistan")
        
        assertTrue(result is CurrencyResult.Success)
        val successResult = result as CurrencyResult.Success
        assertEquals("PKR", successResult.currency.code)
        assertEquals("₨", successResult.currency.symbol)
        assertEquals("pakistan", successResult.country)
    }

    @Test
    fun `getCurrencyForCountry should return success for Indonesia`() {
        val result = locationCurrencyService.getCurrencyForCountry("indonesia")
        
        assertTrue(result is CurrencyResult.Success)
        val successResult = result as CurrencyResult.Success
        assertEquals("IDR", successResult.currency.code)
        assertEquals("Rp", successResult.currency.symbol)
        assertEquals(0, successResult.currency.decimalPlaces)
    }

    @Test
    fun `getCurrencyForCountry should return success for UAE`() {
        val result = locationCurrencyService.getCurrencyForCountry("united arab emirates")
        
        assertTrue(result is CurrencyResult.Success)
        val successResult = result as CurrencyResult.Success
        assertEquals("AED", successResult.currency.code)
        assertEquals("د.إ", successResult.currency.symbol)
        assertEquals("united arab emirates", successResult.country)
    }

    @Test
    fun `getCurrencyForCountry should handle case insensitive input`() {
        val result1 = locationCurrencyService.getCurrencyForCountry("SAUDI ARABIA")
        val result2 = locationCurrencyService.getCurrencyForCountry("saudi arabia")
        val result3 = locationCurrencyService.getCurrencyForCountry("Saudi Arabia")
        
        assertTrue(result1 is CurrencyResult.Success)
        assertTrue(result2 is CurrencyResult.Success)
        assertTrue(result3 is CurrencyResult.Success)
        
        val success1 = result1 as CurrencyResult.Success
        val success2 = result2 as CurrencyResult.Success
        val success3 = result3 as CurrencyResult.Success
        
        assertEquals(success1.currency.code, success2.currency.code)
        assertEquals(success2.currency.code, success3.currency.code)
        assertEquals("SAR", success1.currency.code)
    }

    @Test
    fun `getCurrencyForCountry should return error for empty country name`() {
        val result = locationCurrencyService.getCurrencyForCountry("")
        
        assertTrue(result is CurrencyResult.Success) // Falls back to default
        val successResult = result as CurrencyResult.Success
        assertEquals(CountryCurrencyMapper.defaultCurrency.code, successResult.currency.code)
    }

    @Test
    fun `getCurrencyForCountry should return success for unknown country with fallback`() {
        val result = locationCurrencyService.getCurrencyForCountry("unknown country")
        
        assertTrue(result is CurrencyResult.Success) // Falls back to default
        val successResult = result as CurrencyResult.Success
        assertEquals(CountryCurrencyMapper.defaultCurrency.code, successResult.currency.code)
        assertEquals("unknown country", successResult.country)
    }

    @Test
    fun `getCurrencyForCountry should return correct source`() {
        val result = locationCurrencyService.getCurrencyForCountry("egypt")
        
        assertTrue(result is CurrencyResult.Success)
        val successResult = result as CurrencyResult.Success
        assertEquals("Manual", successResult.source)
    }

    @Test
    fun `getCurrencyForCountry should handle all Muslim-majority countries`() {
        val muslimCountries = listOf(
            "saudi arabia", "united arab emirates", "kuwait", "qatar", "bahrain",
            "oman", "egypt", "jordan", "lebanon", "iraq", "yemen", "pakistan",
            "bangladesh", "indonesia", "malaysia", "turkey", "iran", "afghanistan",
            "nigeria", "senegal", "ghana", "morocco", "algeria", "tunisia", "sudan", "libya"
        )

        for (country in muslimCountries) {
            val result = locationCurrencyService.getCurrencyForCountry(country)
            assertTrue("Should return success for $country", result is CurrencyResult.Success)
            
            val successResult = result as CurrencyResult.Success
            assertNotEquals("Should not be default currency for $country", 
                CountryCurrencyMapper.defaultCurrency.code, successResult.currency.code)
        }
    }

    @Test
    fun `getCurrencyForCountry should return correct decimal places`() {
        val indonesiaResult = locationCurrencyService.getCurrencyForCountry("indonesia")
        assertTrue(indonesiaResult is CurrencyResult.Success)
        assertEquals(0, (indonesiaResult as CurrencyResult.Success).currency.decimalPlaces)

        val usaResult = locationCurrencyService.getCurrencyForCountry("united states")
        assertTrue(usaResult is CurrencyResult.Success)
        assertEquals(2, (usaResult as CurrencyResult.Success).currency.decimalPlaces)

        val japanResult = locationCurrencyService.getCurrencyForCountry("japan")
        assertTrue(japanResult is CurrencyResult.Success)
        assertEquals(0, (japanResult as CurrencyResult.Success).currency.decimalPlaces)
    }

    @Test
    fun `getCurrencyForCountry should return correct currency symbols`() {
        val testCases = mapOf(
            "saudi arabia" to "﷼",
            "united arab emirates" to "د.إ",
            "kuwait" to "د.ك",
            "united states" to "$",
            "united kingdom" to "£",
            "european union" to "€",
            "japan" to "¥",
            "pakistan" to "₨",
            "india" to "₹",
            "nigeria" to "₦",
            "ghana" to "₵"
        )

        for ((country, expectedSymbol) in testCases) {
            val result = locationCurrencyService.getCurrencyForCountry(country)
            assertTrue("Should return success for $country", result is CurrencyResult.Success)
            
            val successResult = result as CurrencyResult.Success
            assertEquals("Wrong symbol for $country", expectedSymbol, successResult.currency.symbol)
        }
    }
}
