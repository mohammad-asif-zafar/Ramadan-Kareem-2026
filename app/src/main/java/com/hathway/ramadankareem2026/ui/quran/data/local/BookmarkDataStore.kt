package com.hathway.ramadankareem2026.ui.quran.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("ayah_bookmarks")

class BookmarkDataStore(
    private val context: Context
) {

    private val BOOKMARK_KEY = stringSetPreferencesKey("bookmarked_ayahs")

    /** Emits all bookmarked ayah IDs */
    val bookmarkedAyahs: Flow<Set<String>> =
        context.dataStore.data.map { prefs ->
            prefs[BOOKMARK_KEY] ?: emptySet()
        }

    suspend fun toggleBookmark(ayahKey: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[BOOKMARK_KEY] ?: emptySet()
            prefs[BOOKMARK_KEY] =
                if (current.contains(ayahKey)) current - ayahKey
                else current + ayahKey
        }
    }
}
