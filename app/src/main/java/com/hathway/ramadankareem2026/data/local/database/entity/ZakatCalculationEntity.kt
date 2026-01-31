package com.hathway.ramadankareem2026.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "zakat_calculations")
data class ZakatCalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val goldValue: Double = 0.0,
    val silverValue: Double = 0.0,
    val cash: Double = 0.0,
    val debts: Double = 0.0,
    val nisabType: String = "", // "gold" or "silver"
    val totalAssets: Double = 0.0,
    val totalLiabilities: Double = 0.0,
    val zakatPayable: Double = 0.0,
    val calculationDate: Date,
    val formattedDate: String = "", // e.g., "May 15, 2023"
    // Currency information
    val currencyCode: String = "USD", // e.g., "USD", "KWD", "EUR"
    val currencySymbol: String = "$", // e.g., "$", "د.ك", "€"
    val currencyName: String = "US Dollar", // e.g., "US Dollar", "Kuwaiti Dinar"
    val country: String = "" // e.g., "United States", "Kuwait"
)
