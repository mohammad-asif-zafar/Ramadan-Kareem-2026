package com.hathway.ramadankareem2026.core.currency

import android.content.Context
import com.hathway.ramadankareem2026.core.location.LocationProvider

/**
 * Service that fetches location and determines the appropriate currency for Zakat calculations.
 */
class LocationCurrencyService(
    context: Context
) {
    private val locationProvider = LocationProvider(context)
    
    /**
     * Fetches the current location and returns the appropriate currency.
     */
    suspend fun getCurrentCurrency(): CurrencyResult {
        return try {
            val locationResult = locationProvider.fetchLocation()
            
            if (locationResult != null) {
                val currency = CountryCurrencyMapper.getCurrencyByCountryName(locationResult.country)
                CurrencyResult.Success(
                    currency = currency,
                    country = locationResult.country,
                    city = locationResult.city,
                    source = "Location"
                )
            } else {
                // Fallback to default currency if location fails
                CurrencyResult.Success(
                    currency = CountryCurrencyMapper.defaultCurrency,
                    country = "Unknown",
                    city = "Unknown",
                    source = "Default"
                )
            }
        } catch (e: Exception) {
            CurrencyResult.Error("Failed to get currency: ${e.message}")
        }
    }
    

    /**
     * Get currency for a specific country name.
     */
    fun getCurrencyForCountry(countryName: String): CurrencyResult {
        return try {
            val currency = CountryCurrencyMapper.getCurrencyByCountryName(countryName)
            CurrencyResult.Success(
                currency = currency,
                country = countryName,
                city = "Unknown",
                source = "Manual"
            )
        } catch (e: Exception) {
            CurrencyResult.Error("Failed to get currency for country: ${e.message}")
        }
    }
}

/**
 * Sealed class for currency result states.
 */
sealed class CurrencyResult {
    data class Success(
        val currency: CountryCurrencyMapper.CurrencyInfo,
        val country: String,
        val city: String,
        val source: String
    ) : CurrencyResult()
    
    data class Error(
        val message: String
    ) : CurrencyResult()
}
