package com.hathway.ramadankareem2026.ui.qibla.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.qiblaDataStore by preferencesDataStore("qibla_prefs")

class QiblaPreferencesDataStore(private val context: Context) {

    companion object {
        val VIBRATION = booleanPreferencesKey("vibration")
        val ALIGNMENT_TEXT = booleanPreferencesKey("alignment_text")
        val CALIBRATION_HINT = booleanPreferencesKey("calibration_hint")
    }

    suspend fun savePreferences(prefs: QiblaPreferences) {
        context.qiblaDataStore.edit { store ->
            store[VIBRATION] = prefs.vibrationEnabled
            store[ALIGNMENT_TEXT] = prefs.showAlignmentText
            store[CALIBRATION_HINT] = prefs.showCalibrationHint
        }
    }

    suspend fun getPreferences(): QiblaPreferences {
        val store = context.qiblaDataStore.data.first()

        return QiblaPreferences(
            vibrationEnabled = store[VIBRATION] ?: true,
            showAlignmentText = store[ALIGNMENT_TEXT] ?: true,
            showCalibrationHint = store[CALIBRATION_HINT] ?: true
        )
    }
}
