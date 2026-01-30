package com.hathway.ramadankareem2026.core.currency


/**
 * Maps countries to their respective currencies for Zakat calculations.
 * Provides currency symbols and formatting information.
 */
object CountryCurrencyMapper {
    
    /**
     * Get currency information for a given country.
     */
    fun getCurrencyForCountry(countryName: String): CurrencyInfo {
        return countryCurrencyMap[countryName.lowercase()] ?: defaultCurrency
    }
    
    /**
     * Get currency information by country code (ISO 3166-1 alpha-2).
     */
    fun getCurrencyForCountryCode(countryCode: String): CurrencyInfo {
        return countryCodeCurrencyMap[countryCode.uppercase()] ?: defaultCurrency
    }
    
    /**
     * Try to get currency by various country name formats.
     */
    fun getCurrencyByCountryName(countryName: String): CurrencyInfo {
        val normalizedCountry = countryName.lowercase().trim()
        
        // Direct match
        countryCurrencyMap[normalizedCountry]?.let { return it }
        
        // Try partial matching for common variations
        for ((country, currency) in countryCurrencyMap) {
            if (country.contains(normalizedCountry) || normalizedCountry.contains(country)) {
                return currency
            }
        }
        
        return defaultCurrency
    }
    
    data class CurrencyInfo(
        val code: String,           // USD, EUR, SAR, etc.
        val symbol: String,         // $, €, ﷼, etc.
        val name: String,           // US Dollar, Euro, Saudi Riyal, etc.
        val decimalPlaces: Int = 2  // Number of decimal places
    )
    
    val defaultCurrency = CurrencyInfo(
        code = "MYR",
        symbol = "RM",
        name = "Malaysian Ringgit",
        decimalPlaces = 2
    )
    
    private val countryCurrencyMap = mapOf(
        // Major Muslim countries
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
        "indonesia" to CurrencyInfo("IDR", "Rp", "Indonesian Rupiah", 0),
        "malaysia" to CurrencyInfo("MYR", "RM", "Malaysian Ringgit"),
        "turkey" to CurrencyInfo("TRY", "₺", "Turkish Lira"),
        "iran" to CurrencyInfo("IRR", "﷼", "Iranian Rial", 0),
        "afghanistan" to CurrencyInfo("AFN", "؋", "Afghan Afghani"),
        "nigeria" to CurrencyInfo("NGN", "₦", "Nigerian Naira"),
        "senegal" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "mali" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "niger" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "burkina faso" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "ivory coast" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "togo" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "benin" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "guinea" to CurrencyInfo("GNF", "FG", "Guinean Franc"),
        "sierra leone" to CurrencyInfo("SLL", "Le", "Sierra Leonean Leone"),
        "liberia" to CurrencyInfo("LRD", "$", "Liberian Dollar"),
        "ghana" to CurrencyInfo("GHS", "₵", "Ghanaian Cedi"),
        "gambia" to CurrencyInfo("GMD", "D", "Gambian Dalasi"),
        "guinea-bissau" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "sudan" to CurrencyInfo("SDG", "ج.س.", "Sudanese Pound"),
        "libya" to CurrencyInfo("LYD", "ل.د", "Libyan Dinar"),
        "tunisia" to CurrencyInfo("TND", "د.ت", "Tunisian Dinar"),
        "algeria" to CurrencyInfo("DZD", "د.ج", "Algerian Dinar"),
        "morocco" to CurrencyInfo("MAD", "د.م.", "Moroccan Dirham"),
        
        // Other major countries
        "united states" to CurrencyInfo("USD", "$", "US Dollar"),
        "united kingdom" to CurrencyInfo("GBP", "£", "British Pound"),
        "canada" to CurrencyInfo("CAD", "$", "Canadian Dollar"),
        "australia" to CurrencyInfo("AUD", "$", "Australian Dollar"),
        "european union" to CurrencyInfo("EUR", "€", "Euro"),
        "germany" to CurrencyInfo("EUR", "€", "Euro"),
        "france" to CurrencyInfo("EUR", "€", "Euro"),
        "italy" to CurrencyInfo("EUR", "€", "Euro"),
        "spain" to CurrencyInfo("EUR", "€", "Euro"),
        "netherlands" to CurrencyInfo("EUR", "€", "Euro"),
        "belgium" to CurrencyInfo("EUR", "€", "Euro"),
        "india" to CurrencyInfo("INR", "₹", "Indian Rupee"),
        "china" to CurrencyInfo("CNY", "¥", "Chinese Yuan"),
        "japan" to CurrencyInfo("JPY", "¥", "Japanese Yen", 0),
        "russia" to CurrencyInfo("RUB", "₽", "Russian Ruble"),
        "brazil" to CurrencyInfo("BRL", "R$", "Brazilian Real"),
        "mexico" to CurrencyInfo("MXN", "$", "Mexican Peso"),
        "south africa" to CurrencyInfo("ZAR", "R", "South African Rand"),
        "singapore" to CurrencyInfo("SGD", "$", "Singapore Dollar"),
        "new zealand" to CurrencyInfo("NZD", "$", "New Zealand Dollar"),
        "switzerland" to CurrencyInfo("CHF", "CHF", "Swiss Franc"),
        "sweden" to CurrencyInfo("SEK", "kr", "Swedish Krona"),
        "norway" to CurrencyInfo("NOK", "kr", "Norwegian Krone"),
        "denmark" to CurrencyInfo("DKK", "kr", "Danish Krone"),
        "poland" to CurrencyInfo("PLN", "zł", "Polish Zloty"),
        "czech republic" to CurrencyInfo("CZK", "Kč", "Czech Koruna")
    )
    
    private val countryCodeCurrencyMap = mapOf(
        "SA" to CurrencyInfo("SAR", "﷼", "Saudi Riyal"),
        "AE" to CurrencyInfo("AED", "د.إ", "UAE Dirham"),
        "KW" to CurrencyInfo("KWD", "د.ك", "Kuwaiti Dinar"),
        "QA" to CurrencyInfo("QAR", "ر.ق", "Qatari Riyal"),
        "BH" to CurrencyInfo("BHD", "ب.د", "Bahraini Dinar"),
        "OM" to CurrencyInfo("OMR", "ر.ع.", "Omani Rial"),
        "EG" to CurrencyInfo("EGP", "ج.م", "Egyptian Pound"),
        "JO" to CurrencyInfo("JOD", "د.أ", "Jordanian Dinar"),
        "LB" to CurrencyInfo("LBP", "ل.ل", "Lebanese Pound"),
        "IQ" to CurrencyInfo("IQD", "ع.د", "Iraqi Dinar"),
        "YE" to CurrencyInfo("YER", "ر.ي", "Yemeni Rial"),
        "PK" to CurrencyInfo("PKR", "₨", "Pakistani Rupee"),
        "BD" to CurrencyInfo("BDT", "৳", "Bangladeshi Taka"),
        "ID" to CurrencyInfo("IDR", "Rp", "Indonesian Rupiah", 0),
        "MY" to CurrencyInfo("MYR", "RM", "Malaysian Ringgit"),
        "TR" to CurrencyInfo("TRY", "₺", "Turkish Lira"),
        "IR" to CurrencyInfo("IRR", "﷼", "Iranian Rial", 0),
        "AF" to CurrencyInfo("AFN", "؋", "Afghan Afghani"),
        "NG" to CurrencyInfo("NGN", "₦", "Nigerian Naira"),
        "SN" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "ML" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "NE" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "BF" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "CI" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "TG" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "BJ" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "GN" to CurrencyInfo("GNF", "FG", "Guinean Franc"),
        "SL" to CurrencyInfo("SLL", "Le", "Sierra Leonean Leone"),
        "LR" to CurrencyInfo("LRD", "$", "Liberian Dollar"),
        "GH" to CurrencyInfo("GHS", "₵", "Ghanaian Cedi"),
        "GM" to CurrencyInfo("GMD", "D", "Gambian Dalasi"),
        "GW" to CurrencyInfo("XOF", "CFA", "West African CFA Franc"),
        "SD" to CurrencyInfo("SDG", "ج.س.", "Sudanese Pound"),
        "LY" to CurrencyInfo("LYD", "ل.د", "Libyan Dinar"),
        "TN" to CurrencyInfo("TND", "د.ت", "Tunisian Dinar"),
        "DZ" to CurrencyInfo("DZD", "د.ج", "Algerian Dinar"),
        "MA" to CurrencyInfo("MAD", "د.م.", "Moroccan Dirham"),
        "US" to CurrencyInfo("USD", "$", "US Dollar"),
        "GB" to CurrencyInfo("GBP", "£", "British Pound"),
        "CA" to CurrencyInfo("CAD", "$", "Canadian Dollar"),
        "AU" to CurrencyInfo("AUD", "$", "Australian Dollar"),
        "IN" to CurrencyInfo("INR", "₹", "Indian Rupee"),
        "CN" to CurrencyInfo("CNY", "¥", "Chinese Yuan"),
        "JP" to CurrencyInfo("JPY", "¥", "Japanese Yen", 0),
        "RU" to CurrencyInfo("RUB", "₽", "Russian Ruble"),
        "BR" to CurrencyInfo("BRL", "R$", "Brazilian Real"),
        "MX" to CurrencyInfo("MXN", "$", "Mexican Peso"),
        "ZA" to CurrencyInfo("ZAR", "R", "South African Rand"),
        "SG" to CurrencyInfo("SGD", "$", "Singapore Dollar"),
        "NZ" to CurrencyInfo("NZD", "$", "New Zealand Dollar"),
        "CH" to CurrencyInfo("CHF", "CHF", "Swiss Franc"),
        "SE" to CurrencyInfo("SEK", "kr", "Swedish Krona"),
        "NO" to CurrencyInfo("NOK", "kr", "Norwegian Krone"),
        "DK" to CurrencyInfo("DKK", "kr", "Danish Krone"),
        "PL" to CurrencyInfo("PLN", "zł", "Polish Zloty"),
        "CZ" to CurrencyInfo("CZK", "Kč", "Czech Koruna")
    )
}
