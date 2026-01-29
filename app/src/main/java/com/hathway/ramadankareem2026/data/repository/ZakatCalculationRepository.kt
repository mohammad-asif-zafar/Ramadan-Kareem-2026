package com.hathway.ramadankareem2026.data.repository

import com.hathway.ramadankareem2026.data.local.database.dao.ZakatCalculationDao
import com.hathway.ramadankareem2026.data.local.database.entity.ZakatCalculationEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class ZakatCalculationRepository(
    private val zakatCalculationDao: ZakatCalculationDao
) {
    
    suspend fun saveCalculation(
        goldValue: Double,
        silverValue: Double,
        cash: Double,
        debts: Double,
        nisabType: String,
        totalAssets: Double,
        totalLiabilities: Double,
        zakatPayable: Double
    ): Long {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        
        val calculation = ZakatCalculationEntity(
            goldValue = goldValue,
            silverValue = silverValue,
            cash = cash,
            debts = debts,
            nisabType = nisabType,
            totalAssets = totalAssets,
            totalLiabilities = totalLiabilities,
            zakatPayable = zakatPayable,
            calculationDate = currentDate,
            formattedDate = formattedDate
        )
        
        return zakatCalculationDao.insertCalculation(calculation)
    }
    
    fun getAllCalculations(): Flow<List<ZakatCalculationEntity>> {
        return zakatCalculationDao.getAllCalculations()
    }
    
    fun getRecentCalculations(limit: Int = 10): Flow<List<ZakatCalculationEntity>> {
        return zakatCalculationDao.getRecentCalculations(limit)
    }
    
    fun getCalculationsCount(): Flow<Int> {
        return zakatCalculationDao.getCalculationsCount()
    }
    
    suspend fun deleteCalculation(calculation: ZakatCalculationEntity) {
        zakatCalculationDao.deleteCalculation(calculation)
    }
    
    suspend fun deleteAllCalculations() {
        zakatCalculationDao.deleteAllCalculations()
    }
    
    suspend fun getCalculationById(id: Long): ZakatCalculationEntity? {
        return zakatCalculationDao.getCalculationById(id)
    }
}
