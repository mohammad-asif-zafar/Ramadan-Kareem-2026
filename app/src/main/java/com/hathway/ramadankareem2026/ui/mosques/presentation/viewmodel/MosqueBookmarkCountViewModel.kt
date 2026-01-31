package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

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

class MosqueBookmarkCountViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _mosqueBookmarkCount = MutableStateFlow(0)
    val mosqueBookmarkCount: StateFlow<Int> = _mosqueBookmarkCount.asStateFlow()

    init {
        loadMosqueBookmarkCount()
    }

    private fun loadMosqueBookmarkCount() {
        viewModelScope.launch {
            try {
                // Get only mosque bookmarks count
                val mosqueCount = bookmarkDao.getMosqueBookmarkCount()
                _mosqueBookmarkCount.value = mosqueCount
                Log.d("MosqueBookmarkCount", "Mosque bookmark count: $mosqueCount")
            } catch (e: Exception) {
                Log.e("MosqueBookmarkCount", "Error loading mosque bookmark count", e)
                _mosqueBookmarkCount.value = 0
            }
        }
    }

    fun refreshMosqueBookmarkCount() {
        loadMosqueBookmarkCount()
    }

    // Update count with delta for immediate UI updates
    fun updateMosqueBookmarkCountImmediate(delta: Int) {
        val newCount = (_mosqueBookmarkCount.value + delta).coerceAtLeast(0)
        _mosqueBookmarkCount.value = newCount
        Log.d("MosqueBookmarkCount", "Updated mosque bookmark count by $delta to $newCount")
        
        // Refresh from database for accuracy
        loadMosqueBookmarkCount()
    }
}
