package com.hathway.ramadankareem2026.data.local.database.dao

import androidx.room.*
import com.hathway.ramadankareem2026.data.local.database.entity.ZakatCalculationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZakatCalculationDao {
    
    @Insert
    suspend fun insertCalculation(calculation: ZakatCalculationEntity): Long
    
    @Query("SELECT * FROM zakat_calculations ORDER BY calculationDate DESC")
    fun getAllCalculations(): Flow<List<ZakatCalculationEntity>>
    
    @Query("SELECT * FROM zakat_calculations ORDER BY calculationDate DESC LIMIT :limit")
    fun getRecentCalculations(limit: Int): Flow<List<ZakatCalculationEntity>>
    
    @Query("SELECT COUNT(*) FROM zakat_calculations")
    fun getCalculationsCount(): Flow<Int>
    
    @Delete
    suspend fun deleteCalculation(calculation: ZakatCalculationEntity)
    
    @Query("DELETE FROM zakat_calculations")
    suspend fun deleteAllCalculations()
    
    @Query("SELECT * FROM zakat_calculations WHERE id = :id")
    suspend fun getCalculationById(id: Long): ZakatCalculationEntity?
}
