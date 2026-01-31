package com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel

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

class AllahNameBookmarkCountViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _allahNameBookmarkCount = MutableStateFlow(0)
    val allahNameBookmarkCount: StateFlow<Int> = _allahNameBookmarkCount.asStateFlow()

    init {
        loadAllahNameBookmarkCount()
    }

    private fun loadAllahNameBookmarkCount() {
        viewModelScope.launch {
            try {
                // Get only allah_name bookmarks count
                val allahNameCount = bookmarkDao.getAllahNameBookmarkCount()
                _allahNameBookmarkCount.value = allahNameCount
                Log.d("AllahNameBookmarkCount", "Allah Name bookmark count: $allahNameCount")
            } catch (e: Exception) {
                Log.e("AllahNameBookmarkCount", "Error loading allah name bookmark count", e)
                _allahNameBookmarkCount.value = 0
            }
        }
    }

    fun refreshAllahNameBookmarkCount() {
        loadAllahNameBookmarkCount()
    }

    // Update count with delta for immediate UI updates
    fun updateAllahNameBookmarkCountImmediate(delta: Int) {
        val newCount = (_allahNameBookmarkCount.value + delta).coerceAtLeast(0)
        _allahNameBookmarkCount.value = newCount
        Log.d("AllahNameBookmarkCount", "Updated allah name bookmark count by $delta to $newCount")
        
        // Refresh from database for accuracy
        loadAllahNameBookmarkCount()
    }
}
