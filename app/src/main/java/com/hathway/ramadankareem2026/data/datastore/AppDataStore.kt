package com.hathway.ramadankareem2026.data.datastore


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("app_prefs")

class AppDataStore(private val context: Context) {

    private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")

    val isFirstLaunch: Flow<Boolean> = context.dataStore.data.map {
        it[FIRST_LAUNCH] ?: true
    }

    suspend fun setLaunched() {
        context.dataStore.edit {
            it[FIRST_LAUNCH] = false
        }
    }
}
