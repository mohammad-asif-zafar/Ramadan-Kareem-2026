package com.hathway.ramadankareem2026.core.currency

import org.junit.Test
import org.junit.Assert.*

class CountryCurrencyMapperTest {

    @Test
    fun `getCurrencyForCountry should return correct currency for Saudi Arabia`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("saudi arabia")
        
        assertEquals("SAR", result.code)
        assertEquals("﷼", result.symbol)
        assertEquals("Saudi Riyal", result.name)
    }

    @Test
    fun `getCurrencyForCountry should return correct currency for United Arab Emirates`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("united arab emirates")
        
        assertEquals("AED", result.code)
        assertEquals("د.إ", result.symbol)
        assertEquals("UAE Dirham", result.name)
    }

    @Test
    fun `getCurrencyForCountry should return correct currency for United States`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("united states")
        
        assertEquals("USD", result.code)
        assertEquals("$", result.symbol)
        assertEquals("US Dollar", result.name)
    }

    @Test
    fun `getCurrencyForCountry should return correct currency for Pakistan`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("pakistan")
        
        assertEquals("PKR", result.code)
        assertEquals("₨", result.symbol)
        assertEquals("Pakistani Rupee", result.name)
    }

    @Test
    fun `getCurrencyForCountry should return correct currency for Indonesia`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("indonesia")
        
        assertEquals("IDR", result.code)
        assertEquals("Rp", result.symbol)
        assertEquals("Indonesian Rupiah", result.name)
        assertEquals(0, result.decimalPlaces)
    }

    @Test
    fun `getCurrencyForCountry should return default currency for unknown country`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("unknown country")
        
        assertEquals(CountryCurrencyMapper.defaultCurrency, result)
    }

    @Test
    fun `getCurrencyForCountry should be case insensitive`() {
        val result1 = CountryCurrencyMapper.getCurrencyForCountry("SAUDI ARABIA")
        val result2 = CountryCurrencyMapper.getCurrencyForCountry("saudi arabia")
        val result3 = CountryCurrencyMapper.getCurrencyForCountry("Saudi Arabia")
        
        assertEquals(result1, result2)
        assertEquals(result2, result3)
        assertEquals("SAR", result1.code)
    }

    @Test
    fun `getCurrencyForCountryCode should return correct currency for US`() {
        val result = CountryCurrencyMapper.getCurrencyForCountryCode("US")
        
        assertEquals("USD", result.code)
        assertEquals("$", result.symbol)
        assertEquals("US Dollar", result.name)
    }

    @Test
    fun `getCurrencyForCountryCode should return correct currency for Saudi Arabia`() {
        val result = CountryCurrencyMapper.getCurrencyForCountryCode("SA")
        
        assertEquals("SAR", result.code)
        assertEquals("﷼", result.symbol)
        assertEquals("Saudi Riyal", result.name)
    }

    @Test
    fun `getCurrencyForCountryCode should return default currency for unknown code`() {
        val result = CountryCurrencyMapper.getCurrencyForCountryCode("XX")
        
        assertEquals(CountryCurrencyMapper.defaultCurrency, result)
    }

    @Test
    fun `getCurrencyForCountryCode should be case insensitive`() {
        val result1 = CountryCurrencyMapper.getCurrencyForCountryCode("SA")
        val result2 = CountryCurrencyMapper.getCurrencyForCountryCode("sa")
        
        assertEquals(result1, result2)
        assertEquals("SAR", result1.code)
    }

    @Test
    fun `getCurrencyByCountryName should handle partial matches`() {
        val result = CountryCurrencyMapper.getCurrencyByCountryName("arabia")
        
        assertEquals("SAR", result.code)
        assertEquals("Saudi Riyal", result.name)
    }

    @Test
    fun `getCurrencyByCountryName should handle variations`() {
        val result1 = CountryCurrencyMapper.getCurrencyByCountryName("USA")
        val result2 = CountryCurrencyMapper.getCurrencyByCountryName("United States of America")
        
        assertEquals("USD", result1.code)
        assertEquals("USD", result2.code)
    }

    @Test
    fun `default currency should be USD`() {
        val default = CountryCurrencyMapper.defaultCurrency
        
        assertEquals("USD", default.code)
        assertEquals("$", default.symbol)
        assertEquals("US Dollar", default.name)
        assertEquals(2, default.decimalPlaces)
    }

    @Test
    fun `should return correct currency for Kuwait`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("kuwait")
        assertEquals("KWD", result.code)
    }

    @Test
    fun `should return correct currency for Qatar`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("qatar")
        assertEquals("QAR", result.code)
    }

    @Test
    fun `should return correct currency for Bahrain`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("bahrain")
        assertEquals("BHD", result.code)
    }

    @Test
    fun `should return correct currency for Oman`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("oman")
        assertEquals("OMR", result.code)
    }

    @Test
    fun `should return correct currency for Egypt`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("egypt")
        assertEquals("EGP", result.code)
    }

    @Test
    fun `should return correct currency for Jordan`() {
        val result = CountryCurrencyMapper.getCurrencyForCountry("jordan")
        assertEquals("JOD", result.code)
    }
}
