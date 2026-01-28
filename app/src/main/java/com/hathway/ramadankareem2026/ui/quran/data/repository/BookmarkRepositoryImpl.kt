package com.hathway.ramadankareem2026.ui.quran.data.repository


import com.hathway.ramadankareem2026.ui.quran.data.local.BookmarkDataStore
import com.hathway.ramadankareem2026.ui.quran.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val dataStore: BookmarkDataStore
) : BookmarkRepository {

    override val bookmarkedAyahs: Flow<Set<String>> = dataStore.bookmarkedAyahs

    override suspend fun toggleBookmark(ayahKey: String) = dataStore.toggleBookmark(ayahKey)
}
