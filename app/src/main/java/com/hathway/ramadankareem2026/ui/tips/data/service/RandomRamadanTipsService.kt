package com.hathway.ramadankareem2026.ui.tips.data.service

import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
import com.hathway.ramadankareem2026.ui.tips.data.source.TipsDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service for providing random Ramadan tips with multi-language support.
 * Follows clean architecture principles and provides reactive state management.
 */
@Singleton
class RandomRamadanTipsService @Inject constructor(
    private val tipsDataSource: TipsDataSource = TipsDataSource
) {
    
    private val random = Random()
    private val _currentRandomTip = MutableStateFlow<Tip?>(null)
    val currentRandomTip: StateFlow<Tip?> = _currentRandomTip.asStateFlow()
    
    private val usedTips = mutableSetOf<Int>()
    
    /**
     * Gets a random Ramadan tip from all available tips.
     * Ensures no immediate repetition of the same tip.
     */
    fun getRandomRamadanTip(): Tip {
        val allTips = tipsDataSource.getAllTips()
        val availableTips = allTips.filter { it.id !in usedTips }
        
        val selectedTip = if (availableTips.isNotEmpty()) {
            val tip = availableTips[random.nextInt(availableTips.size)]
            usedTips.add(tip.id)
            
            // Reset used tips when all have been shown
            if (usedTips.size >= allTips.size) {
                usedTips.clear()
            }
            
            tip
        } else {
            // Fallback: pick any tip if all have been used
            allTips[random.nextInt(allTips.size)].also { 
                usedTips.clear() // Reset for next cycle
            }
        }
        
        _currentRandomTip.value = selectedTip
        return selectedTip
    }
    
    /**
     * Gets a random tip from a specific category.
     */
    fun getRandomTipFromCategory(category: TipCategory): Tip {
        val allTips = tipsDataSource.getAllTips()
        val categoryTips = allTips.filter { it.category == category }
        
        return if (categoryTips.isNotEmpty()) {
            categoryTips[random.nextInt(categoryTips.size)]
        } else {
            // Fallback to any tip if category is empty
            getRandomRamadanTip()
        }
    }
    
    /**
     * Gets multiple random tips for variety.
     */
    fun getRandomTips(count: Int): List<Tip> {
        val allTips = tipsDataSource.getAllTips().shuffled(random)
        return allTips.take(count.coerceAtMost(allTips.size))
    }
    
    /**
     * Gets a random daily Ramadan tip specifically.
     */
    fun getRandomDailyRamadanTip(): Tip {
        val allTips = tipsDataSource.getAllTips()
        val dailyTips = allTips.filter { it.category == TipCategory.DAILY_RAMADAN }
        
        return if (dailyTips.isNotEmpty()) {
            dailyTips[random.nextInt(dailyTips.size)]
        } else {
            getRandomRamadanTip()
        }
    }
    
    /**
     * Resets the used tips tracking.
     */
    fun resetUsedTips() {
        usedTips.clear()
    }
}
