package com.hathway.ramadankareem2026.ui.prayer.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.api.NetworkModule

class PrayerViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrayerViewModel::class.java)) {

            val repository = PrayerRepository(
                context = app, api = NetworkModule.prayerApi
            )

            return PrayerViewModel(
                app = app, repository = repository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
