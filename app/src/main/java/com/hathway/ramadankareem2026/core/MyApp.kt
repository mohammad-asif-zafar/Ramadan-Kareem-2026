package com.hathway.ramadankareem2026.core

import android.app.Application
import com.hathway.ramadankareem2026.core.location.LocationDataStore
import com.hathway.ramadankareem2026.data.local.BookmarkManager

class MyApp : Application() {
    lateinit var locationDataStore: LocationDataStore

    override fun onCreate() {
        super.onCreate()
        locationDataStore = LocationDataStore(applicationContext)
        BookmarkManager.init(this)
    }
}
