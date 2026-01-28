package com.hathway.ramadankareem2026.ui.quran.domain.repository

import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    val bookmarkedAyahs: Flow<Set<String>>
    suspend fun toggleBookmark(ayahKey: String)
}
