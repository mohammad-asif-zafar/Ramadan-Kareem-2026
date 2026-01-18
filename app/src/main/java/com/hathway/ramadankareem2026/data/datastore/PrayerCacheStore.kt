package com.hathway.ramadankareem2026.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("prayer_cache")

class PrayerCacheStore(private val context: Context) {

    private val gson = Gson()
    private val KEY = stringPreferencesKey("last_prayer_data")

    suspend fun save(prayers: List<PrayerDomain>) {
        context.dataStore.edit {
            it[KEY] = gson.toJson(prayers)
        }
    }

    suspend fun load(): List<PrayerDomain>? {
        val json = context.dataStore.data.first()[KEY] ?: return null
        return gson.fromJson(
            json, Array<PrayerDomain>::class.java
        )?.toList()
    }
}
