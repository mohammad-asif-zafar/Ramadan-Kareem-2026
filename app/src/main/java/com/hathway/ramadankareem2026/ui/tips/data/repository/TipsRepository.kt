package com.hathway.ramadankareem2026.ui.tips.data.repository

import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.source.TipsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Singleton

@Singleton
class TipsRepository(
    private val dataSource: TipsDataSource = TipsDataSource
) {
    
    private val _dailyTip = MutableStateFlow(dataSource.getDailyTip())

    private val _hadithAyahOfTheDay = MutableStateFlow(dataSource.getHadithOrAyahOfTheDay())

    fun getAllTips(): List<Tip> {
        return dataSource.getAllTips()
    }

    
    fun getDailyTip(): Tip {
        return dataSource.getDailyTip()
    }

    fun getTipById(id: Int): Tip? {
        return dataSource.getAllTips().find { it.id == id }
    }

}
