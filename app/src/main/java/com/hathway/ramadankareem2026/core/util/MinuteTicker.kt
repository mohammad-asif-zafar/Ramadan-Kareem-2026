package com.hathway.ramadankareem2026.core.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.LocalDateTime

/**
 * Emits once immediately, then every minute aligned to clock minute
 */
fun minuteTicker(): Flow<Unit> = flow {

    // Emit immediately
    emit(Unit)

    while (true) {
        val now = LocalDateTime.now()
        val nextMinute = now.plusMinutes(1).withSecond(0).withNano(0)
        val delayMillis =
            Duration.between(now, nextMinute).toMillis()

        delay(delayMillis)
        emit(Unit)
    }
}
