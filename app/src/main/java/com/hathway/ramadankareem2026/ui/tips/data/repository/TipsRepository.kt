package com.hathway.ramadankareem2026.ui.tips.data.repository

import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
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
    val dailyTip: Flow<Tip> = _dailyTip.asStateFlow()
    
    private val _hadithAyahOfTheDay = MutableStateFlow(dataSource.getHadithOrAyahOfTheDay())
    val hadithAyahOfTheDay: Flow<Tip> = _hadithAyahOfTheDay.asStateFlow()
    
    fun getAllTips(): List<Tip> {
        return dataSource.getAllTips()
    }
    
    fun getTipsByCategory(category: TipCategory): List<Tip> {
        return dataSource.getTipsByCategory(category)
    }
    
    fun getDailyTip(): Tip {
        return dataSource.getDailyTip()
    }
    
    fun getHadithOrAyahOfTheDay(): Tip {
        return dataSource.getHadithOrAyahOfTheDay()
    }
    
    fun refreshDailyTips() {
        _dailyTip.value = dataSource.getDailyTip()
        _hadithAyahOfTheDay.value = dataSource.getHadithOrAyahOfTheDay()
    }
    
    fun getTipById(id: Int): Tip? {
        return dataSource.getAllTips().find { it.id == id }
    }
    
    fun searchTips(query: String): List<Tip> {
        return dataSource.getAllTips().filter { tip ->
            tip.title.contains(query, ignoreCase = true) ||
            tip.content.contains(query, ignoreCase = true) ||
            tip.category.displayName.contains(query, ignoreCase = true)
        }
    }
}
