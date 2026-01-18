package com.hathway.ramadankareem2026.core

import android.app.Application
import com.hathway.ramadankareem2026.core.location.LocationDataStore

class MyApp : Application() {
    lateinit var locationDataStore: LocationDataStore

    override fun onCreate() {
        super.onCreate()
        locationDataStore = LocationDataStore(applicationContext)
    }
}
