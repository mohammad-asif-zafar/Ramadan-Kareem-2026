package com.hathway.ramadankareem2026.core.time

import java.time.LocalDateTime

interface AppClock {
    fun now(): LocalDateTime
}

class SystemAppClock : AppClock {
    override fun now(): LocalDateTime = LocalDateTime.now()
}