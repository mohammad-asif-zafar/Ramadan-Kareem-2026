package com.hathway.ramadankareem2026.ui.bookmarks.viewmodel

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

class BookmarkCountViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _bookmarkCount = MutableStateFlow(0)
    val bookmarkCount: StateFlow<Int> = _bookmarkCount.asStateFlow()

    init {
        loadBookmarkCount()
    }

    private fun loadBookmarkCount() {
        viewModelScope.launch {
            try {
                // Use the efficient total count method
                val totalCount = bookmarkDao.getTotalBookmarkCount()
                _bookmarkCount.value = totalCount
                Log.d("BookmarkCount", "Total bookmark count: $totalCount")
            } catch (e: Exception) {
                Log.e("BookmarkCount", "Error loading bookmark count", e)
                _bookmarkCount.value = 0
            }
        }
    }
    
    fun refreshBookmarkCount() {
        Log.d("BookmarkCount", "Refreshing bookmark count")
        loadBookmarkCount()
    }
    
    // Add immediate update method for better UX
    fun updateBookmarkCountImmediate(delta: Int) {
        val newCount = (_bookmarkCount.value + delta).coerceAtLeast(0)
        _bookmarkCount.value = newCount
        Log.d("BookmarkCount", "Immediate update: $delta, new count: $newCount")
        
        // Still refresh from database to ensure accuracy
        viewModelScope.launch {
            loadBookmarkCount()
        }
    }
}
