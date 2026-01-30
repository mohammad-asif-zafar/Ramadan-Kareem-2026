package com.hathway.ramadankareem2026.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "zakat_calculations")
data class ZakatCalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val goldValue: Double,
    val silverValue: Double,
    val cash: Double,
    val debts: Double,
    val nisabType: String, // "gold" or "silver"
    val totalAssets: Double,
    val totalLiabilities: Double,
    val zakatPayable: Double,
    val calculationDate: Date,
    val formattedDate: String, // For display purposes
    // Currency information
    val currencyCode: String = "USD", // e.g., "USD", "KWD", "EUR"
    val currencySymbol: String = "$", // e.g., "$", "د.ك", "€"
    val currencyName: String = "US Dollar", // e.g., "US Dollar", "Kuwaiti Dinar"
    val country: String = "" // e.g., "United States", "Kuwait"
)
