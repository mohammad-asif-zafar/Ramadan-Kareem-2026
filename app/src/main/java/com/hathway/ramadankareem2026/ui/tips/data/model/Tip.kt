package com.hathway.ramadankareem2026.ui.tips.data.model

data class Tip(
    val id: Int,
    val title: String,
    val content: String,
    val category: TipCategory,
    val isDaily: Boolean = false
)

enum class TipCategory(
    val displayName: String,
    val priority: Int
) {
    DAILY_RAMADAN("Daily Ramadan Tip", 1),
    FASTING("Fasting Tips", 2),
    PRAYER("Prayer Improvement", 3),
    DUA_DHIKR("Duʿā & Dhikr", 4),
    LIFESTYLE("Ramadan Lifestyle", 5),
    HADITH_AYAH("Hadith / Ayah of the Day", 6),
    GOOD_DEEDS("Good Deeds Checklist", 7)
}
