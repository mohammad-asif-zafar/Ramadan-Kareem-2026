package com.hathway.ramadankareem2026.ui.allahnames.viewmodel

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

class AllahNamesBookmarkViewModel(application: Application) : AndroidViewModel(application) {

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
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "allah_name")
            Log.d("AllahNamesBookmark", "Checking bookmark status for $itemId: $isCurrentlyBookmarked")
            _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }.value =
                isCurrentlyBookmarked
        }
    }

    fun toggleBookmark(itemId: String, title: String, content: String?) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "allah_name")
            val stateFlow = _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }

            Log.d("AllahNamesBookmark", "Toggling bookmark for $itemId: currently $isCurrentlyBookmarked")

            if (isCurrentlyBookmarked) {
                bookmarkDao.deleteBookmarkById(itemId, "allah_name")
                stateFlow.value = false
                Log.d("AllahNamesBookmark", "Removed bookmark for $itemId")
                
                // Trigger immediate badge update with delta
                onBookmarkCountChanged?.invoke(-1)
            } else {
                val bookmark = com.hathway.ramadankareem2026.data.local.database.BookmarkEntity(
                    id = "allah_name_${itemId}",
                    itemId = itemId,
                    itemType = "allah_name",
                    title = title,
                    content = content
                )
                bookmarkDao.insertBookmark(bookmark)
                stateFlow.value = true
                Log.d("AllahNamesBookmark", "Added bookmark for $itemId")
                
                // Trigger immediate badge update with delta
                onBookmarkCountChanged?.invoke(1)
            }
        }
    }
}
