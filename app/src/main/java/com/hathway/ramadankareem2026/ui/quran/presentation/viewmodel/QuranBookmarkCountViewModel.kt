package com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranBookmarkCountViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _quranBookmarkCount = MutableStateFlow(0)
    val quranBookmarkCount: StateFlow<Int> = _quranBookmarkCount.asStateFlow()

    init {
        loadQuranBookmarkCount()
    }

    private fun loadQuranBookmarkCount() {
        viewModelScope.launch {
            try {
                // Get only quran bookmarks count
                val quranCount = bookmarkDao.getQuranBookmarkCount()
                _quranBookmarkCount.value = quranCount
                Log.d("QuranBookmarkCount", "Quran bookmark count: $quranCount")
            } catch (e: Exception) {
                Log.e("QuranBookmarkCount", "Error loading quran bookmark count", e)
                _quranBookmarkCount.value = 0
            }
        }
    }

    fun refreshQuranBookmarkCount() {
        loadQuranBookmarkCount()
    }

    // Update count with delta for immediate UI updates
    fun updateQuranBookmarkCountImmediate(delta: Int) {
        val newCount = (_quranBookmarkCount.value + delta).coerceAtLeast(0)
        _quranBookmarkCount.value = newCount
        Log.d("QuranBookmarkCount", "Updated quran bookmark count by $delta to $newCount")
        
        // Refresh from database for accuracy
        loadQuranBookmarkCount()
    }
}
