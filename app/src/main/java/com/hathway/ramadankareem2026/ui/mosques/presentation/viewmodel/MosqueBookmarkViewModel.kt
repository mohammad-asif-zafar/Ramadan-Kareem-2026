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

class MosqueBookmarkViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _isBookmarkedMap = mutableMapOf<String, MutableStateFlow<Boolean>>()
    
    // Add callback for immediate badge updates
    private var onBookmarkCountChanged: ((Int) -> Unit)? = null

    fun setBookmarkCountChangedCallback(callback: (Int) -> Unit) {
        onBookmarkCountChanged = callback
    }

    fun isBookmarked(itemId: String): StateFlow<Boolean> {
        return _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }.asStateFlow()
    }

    fun checkBookmarkStatus(itemId: String) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "mosque")
            Log.d("MosqueBookmark", "Checking bookmark status for $itemId: $isCurrentlyBookmarked")
            _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }.value =
                isCurrentlyBookmarked
        }
    }

    fun toggleBookmark(itemId: String, title: String, content: String?) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "mosque")
            val stateFlow = _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }

            Log.d("MosqueBookmark", "Toggling bookmark for $itemId: currently $isCurrentlyBookmarked")

            if (isCurrentlyBookmarked) {
                bookmarkDao.deleteBookmarkById(itemId, "mosque")
                stateFlow.value = false
                Log.d("MosqueBookmark", "Removed bookmark for $itemId")
                
                // Trigger immediate badge update with delta
                onBookmarkCountChanged?.invoke(-1)
            } else {
                val bookmark = com.hathway.ramadankareem2026.data.local.database.BookmarkEntity(
                    id = "mosque_${itemId}",
                    itemId = itemId,
                    itemType = "mosque",
                    title = title,
                    content = content
                )
                bookmarkDao.insertBookmark(bookmark)
                stateFlow.value = true
                Log.d("MosqueBookmark", "Added bookmark for $itemId")
                
                // Trigger immediate badge update with delta
                onBookmarkCountChanged?.invoke(1)
            }
        }
    }
}
