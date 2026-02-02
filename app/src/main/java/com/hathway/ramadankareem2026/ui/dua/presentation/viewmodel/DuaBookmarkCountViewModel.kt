package com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel

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

class DuaBookmarkCountViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _duaBookmarkCount = MutableStateFlow(0)
    val duaBookmarkCount: StateFlow<Int> = _duaBookmarkCount.asStateFlow()

    init {
        loadDuaBookmarkCount()
    }

    private fun loadDuaBookmarkCount() {
        viewModelScope.launch {
            try {
                // Get only dua bookmarks count
                val duaCount = bookmarkDao.getDuaBookmarkCount()
                _duaBookmarkCount.value = duaCount
                Log.d("DuaBookmarkCount", "Dua bookmark count: $duaCount")
            } catch (e: Exception) {
                Log.e("DuaBookmarkCount", "Error loading dua bookmark count", e)
                _duaBookmarkCount.value = 0
            }
        }
    }

    // Update count with delta for immediate UI updates
    fun updateDuaBookmarkCountImmediate(delta: Int) {
        val newCount = (_duaBookmarkCount.value + delta).coerceAtLeast(0)
        _duaBookmarkCount.value = newCount
        Log.d("DuaBookmarkCount", "Updated dua bookmark count by $delta to $newCount")
        
        // Refresh from database for accuracy
        loadDuaBookmarkCount()
    }
}
