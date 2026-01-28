package com.hathway.ramadankareem2026.ui.quran.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hathway.ramadankareem2026.core.network.NetworkMonitor
import com.hathway.ramadankareem2026.ui.quran.data.local.BookmarkDataStore
import com.hathway.ramadankareem2026.ui.quran.data.local.LastReadDataStore
import com.hathway.ramadankareem2026.ui.quran.data.local.QuranLocalDataSource
import com.hathway.ramadankareem2026.ui.quran.data.remote.QuranRemoteDataSource
import com.hathway.ramadankareem2026.ui.quran.data.repository.BookmarkRepositoryImpl
import com.hathway.ramadankareem2026.ui.quran.data.repository.QuranRepositoryImpl
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetAyahListUseCase
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetSurahListUseCase

class QuranViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    // ✅ Always use applicationContext
    private val appContext = context.applicationContext

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuranViewModel::class.java)) {

            // 🔹 Data sources
            val localDataSource = QuranLocalDataSource()
            val remoteDataSource = QuranRemoteDataSource()

            // 🔹 Network monitor (safe)
            val networkMonitor = NetworkMonitor(appContext)

            // 🔹 Repository (offline-first)
            val quranRepository = QuranRepositoryImpl(
                remote = remoteDataSource,
                local = localDataSource,
                networkMonitor = networkMonitor
            )

            // 🔹 Use cases
            val getSurahList = GetSurahListUseCase(quranRepository)
            val getAyahs = GetAyahListUseCase(quranRepository)

            // 🔹 Bookmark + last read (DataStore needs app context)
            val bookmarkStore = BookmarkDataStore(appContext)
            val bookmarkRepo = BookmarkRepositoryImpl(bookmarkStore)
            val lastReadStore = LastReadDataStore(appContext)

            @Suppress("UNCHECKED_CAST")
            return QuranViewModel(
                getSurahList = getSurahList,
                getAyahs = getAyahs,
                bookmarkRepository = bookmarkRepo,
                lastReadStore = lastReadStore
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}

