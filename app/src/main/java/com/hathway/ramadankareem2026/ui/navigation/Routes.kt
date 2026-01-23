package com.hathway.ramadankareem2026.ui.navigation

object Routes {
    const val HOME = "home"
    const val QURAN = "quran"
    const val DUA = "dua"
    const val ZAKAT = "zakat"
    const val REMINDER = "reminder"
    const val TIPS = "tips"
    const val TASBIH = "tasbih"
    const val QIBLA = "qibla"
    const val CALENDAR = "calendar"
    const val QIBLA_SETTINGS = "qibla_settings"
    const val RAMADAN_CALENDAR = "ramadan_calendar"
    const val DUA_CATEGORY = "dua_category"
    const val DUA_DETAIL = "dua_detail"
    const val ALLAH_NAMES = "allah_names"
    const val ALLAH_NAME_DETAIL = "allah_name_detail"
    fun allahNameDetail(id: Int) = "$ALLAH_NAME_DETAIL/$id"
    const val ZAKAT_BREAKDOWN = "zakat_breakdown"
    fun zakatBreakdown(): String = ZAKAT_BREAKDOWN
}
