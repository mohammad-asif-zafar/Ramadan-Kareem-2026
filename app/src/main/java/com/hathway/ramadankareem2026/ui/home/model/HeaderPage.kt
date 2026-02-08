package com.hathway.ramadankareem2026.ui.home.model

enum class HeaderType {
    DYNAMIC_PRAYER,
    NEXT_PRAYER,
    REMINDER,
    IFTAR_TIME,
    SUHOOR_TIME
}

data class HeaderPage(
    val type: HeaderType,
    val title: String,
    val subtitle: String,
    val hint: String,
    val isAlarmEnabled: Boolean = false,
    val onAlarmToggle: (() -> Unit)? = null
)
