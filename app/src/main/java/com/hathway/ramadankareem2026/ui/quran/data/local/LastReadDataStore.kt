package com.hathway.ramadankareem2026.ui.quran.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.lastReadStore by preferencesDataStore("last_read_ayah")

class LastReadDataStore(
    private val context: Context
) {

    private val LAST_READ_KEY = stringPreferencesKey("last_read")

    val lastReadAyah: Flow<String?> =
        context.lastReadStore.data.map { prefs ->
            prefs[LAST_READ_KEY]
        }

    suspend fun saveLastRead(surahId: Int, ayahNumber: Int) {
        context.lastReadStore.edit { prefs ->
            prefs[LAST_READ_KEY] = "$surahId:$ayahNumber"
        }
    }
}
