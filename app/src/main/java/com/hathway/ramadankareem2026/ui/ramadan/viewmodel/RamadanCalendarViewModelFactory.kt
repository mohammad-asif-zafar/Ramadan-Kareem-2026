package com.hathway.ramadankareem2026.ui.ramadan.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hathway.ramadankareem2026.core.time.SystemAppClock
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerRepository
import com.hathway.ramadankareem2026.ui.prayer.data.api.NetworkModule

class RamadanCalendarViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RamadanCalendarViewModel::class.java)) {
            
            val prayerRepository = PrayerRepository(
                context = app, 
                api = NetworkModule.prayerApi
            )

            return RamadanCalendarViewModel(
                application = app,
                clock = SystemAppClock(),
                prayerRepository = prayerRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
