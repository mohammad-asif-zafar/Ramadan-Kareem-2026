package com.hathway.ramadankareem2026

/**
 * Test dependencies and utilities for the Zakat currency system
 */
object TestDependencies {
    
    /**
     * Sample location data for testing
     */
    object TestData {
        val SAUDI_ARABIA = LocationTestData(
            country = "Saudi Arabia",
            city = "Riyadh",
            latitude = 24.7136,
            longitude = 46.6753,
            expectedCurrencyCode = "SAR",
            expectedCurrencySymbol = "﷼"
        )
        
        val UAE = LocationTestData(
            country = "United Arab Emirates",
            city = "Dubai",
            latitude = 25.2048,
            longitude = 55.2708,
            expectedCurrencyCode = "AED",
            expectedCurrencySymbol = "د.إ"
        )
        
        val PAKISTAN = LocationTestData(
            country = "Pakistan",
            city = "Karachi",
            latitude = 24.8607,
            longitude = 67.0011,
            expectedCurrencyCode = "PKR",
            expectedCurrencySymbol = "₨"
        )
        
        val INDONESIA = LocationTestData(
            country = "Indonesia",
            city = "Jakarta",
            latitude = -6.2088,
            longitude = 106.8456,
            expectedCurrencyCode = "IDR",
            expectedCurrencySymbol = "Rp"
        )
        
        val USA = LocationTestData(
            country = "United States",
            city = "New York",
            latitude = 40.7128,
            longitude = -74.0060,
            expectedCurrencyCode = "USD",
            expectedCurrencySymbol = "$"
        )
    }
    
    data class LocationTestData(
        val country: String,
        val city: String,
        val latitude: Double,
        val longitude: Double,
        val expectedCurrencyCode: String,
        val expectedCurrencySymbol: String
    )
    
    /**
     * Zakat calculation test data
     */
    object ZakatTestData {
        val SAMPLE_CALCULATION = ZakatCalculationTestData(
            gold = "10000",
            silver = "5000",
            cash = "20000",
            debts = "3000",
            expectedZakatAmount = 800.0
        )
        
        val LARGE_CALCULATION = ZakatCalculationTestData(
            gold = "100000",
            silver = "50000",
            cash = "200000",
            debts = "30000",
            expectedZakatAmount = 8000.0
        )
        
        val MINIMAL_CALCULATION = ZakatCalculationTestData(
            gold = "100",
            silver = "50",
            cash = "200",
            debts = "30",
            expectedZakatAmount = 8.0
        )
    }
    
    data class ZakatCalculationTestData(
        val gold: String,
        val silver: String,
        val cash: String,
        val debts: String,
        val expectedZakatAmount: Double
    )
    
    /**
     * Currency test data for all supported countries
     */
    object CurrencyTestData {
        val ALL_CURRENCIES = mapOf(
            "saudi arabia" to CurrencyInfo("SAR", "﷼", "Saudi Riyal"),
            "united arab emirates" to CurrencyInfo("AED", "د.إ", "UAE Dirham"),
            "kuwait" to CurrencyInfo("KWD", "د.ك", "Kuwaiti Dinar"),
            "qatar" to CurrencyInfo("QAR", "ر.ق", "Qatari Riyal"),
            "bahrain" to CurrencyInfo("BHD", "ب.د", "Bahraini Dinar"),
            "oman" to CurrencyInfo("OMR", "ر.ع.", "Omani Rial"),
            "egypt" to CurrencyInfo("EGP", "ج.م", "Egyptian Pound"),
            "jordan" to CurrencyInfo("JOD", "د.أ", "Jordanian Dinar"),
            "lebanon" to CurrencyInfo("LBP", "ل.ل", "Lebanese Pound"),
            "iraq" to CurrencyInfo("IQD", "ع.د", "Iraqi Dinar"),
            "yemen" to CurrencyInfo("YER", "ر.ي", "Yemeni Rial"),
            "pakistan" to CurrencyInfo("PKR", "₨", "Pakistani Rupee"),
            "bangladesh" to CurrencyInfo("BDT", "৳", "Bangladeshi Taka"),
            "indonesia" to CurrencyInfo("IDR", "Rp", "Indonesian Rupiah"),
            "malaysia" to CurrencyInfo("MYR", "RM", "Malaysian Ringgit"),
            "turkey" to CurrencyInfo("TRY", "₺", "Turkish Lira"),
            "iran" to CurrencyInfo("IRR", "﷼", "Iranian Rial"),
            "afghanistan" to CurrencyInfo("AFN", "؋", "Afghan Afghani"),
            "nigeria" to CurrencyInfo("NGN", "₦", "Nigerian Naira"),
            "senegal" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
            "ghana" to CurrencyInfo("GHS", "₵", "Ghanaian Cedi"),
            "morocco" to CurrencyInfo("MAD", "د.م.", "Moroccan Dirham"),
            "algeria" to CurrencyInfo("DZD", "د.ج", "Algerian Dinar"),
            "tunisia" to CurrencyInfo("TND", "د.ت", "Tunisian Dinar"),
            "sudan" to CurrencyInfo("SDG", "ج.س.", "Sudanese Pound"),
            "libya" to CurrencyInfo("LYD", "ل.د", "Libyan Dinar"),
            "united states" to CurrencyInfo("USD", "$", "US Dollar"),
            "united kingdom" to CurrencyInfo("GBP", "£", "British Pound"),
            "european union" to CurrencyInfo("EUR", "€", "Euro"),
            "japan" to CurrencyInfo("JPY", "¥", "Japanese Yen"),
            "india" to CurrencyInfo("INR", "₹", "Indian Rupee"),
            "china" to CurrencyInfo("CNY", "¥", "Chinese Yuan"),
            "russia" to CurrencyInfo("RUB", "₽", "Russian Ruble"),
            "brazil" to CurrencyInfo("BRL", "R$", "Brazilian Real"),
            "mexico" to CurrencyInfo("MXN", "$", "Mexican Peso"),
            "south africa" to CurrencyInfo("ZAR", "R", "South African Rand"),
            "singapore" to CurrencyInfo("SGD", "$", "Singapore Dollar"),
            "canada" to CurrencyInfo("CAD", "$", "Canadian Dollar"),
            "australia" to CurrencyInfo("AUD", "$", "Australian Dollar")
        )
    }
    
    data class CurrencyInfo(
        val code: String,
        val symbol: String,
        val name: String
    )
    
    /**
     * Error scenarios for testing
     */
    object ErrorScenarios {
        const val PERMISSION_DENIED = "Location permission denied"
        const val GPS_DISABLED = "GPS is disabled"
        const val NETWORK_UNAVAILABLE = "Network is unavailable"
        const val TIMEOUT = "Location request timed out"
        const val UNKNOWN_COUNTRY = "Unknown country detected"
        const val CURRENCY_MAPPING_ERROR = "Failed to map country to currency"
    }
    
    /**
     * Test utilities
     */
    object TestUtils {
        fun assertCurrencyInfo(
            actual: com.hathway.ramadankareem2026.core.currency.CountryCurrencyMapper.CurrencyInfo,
            expected: CurrencyInfo
        ) {
            assert(expected.code == actual.code) { "Currency code mismatch: expected ${expected.code}, got ${actual.code}" }
            assert(expected.symbol == actual.symbol) { "Currency symbol mismatch: expected ${expected.symbol}, got ${actual.symbol}" }
            assert(expected.name == actual.name) { "Currency name mismatch: expected ${expected.name}, got ${actual.name}" }
        }
        
        fun assertLocationResult(
            actual: com.hathway.ramadankareem2026.core.currency.CurrencyResult.Success,
            expected: LocationTestData
        ) {
            assert(expected.country == actual.country) { "Country mismatch: expected ${expected.country}, got ${actual.country}" }
            assert(expected.expectedCurrencyCode == actual.currency.code) { "Currency code mismatch: expected ${expected.expectedCurrencyCode}, got ${actual.currency.code}" }
            assert(expected.expectedCurrencySymbol == actual.currency.symbol) { "Currency symbol mismatch: expected ${expected.expectedCurrencySymbol}, got ${actual.currency.symbol}" }
        }
        
        fun assertZakatCalculation(
            actual: Double,
            expected: Double,
            tolerance: Double = 0.01
        ) {
            val difference = kotlin.math.abs(actual - expected)
            assert(difference <= tolerance) { "Zakat amount mismatch: expected $expected, got $actual (difference: $difference)" }
        }
    }
}
