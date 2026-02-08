package com.hathway.ramadankareem2026.ui.dua.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DuaBookmarkViewModel(application: Application) : AndroidViewModel(application) {

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
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "dua")
            Log.d("DuaBookmark", "Checking bookmark status for $itemId: $isCurrentlyBookmarked")
            _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }.value =
                isCurrentlyBookmarked
        }
    }

    fun toggleBookmark(
        itemId: String,
        title: String,
        content: String?
    ) {
        viewModelScope.launch {

            val bookmarkId = "dua_$itemId"

            val isCurrentlyBookmarked =
                bookmarkDao.isBookmarked(bookmarkId, "dua")

            val stateFlow = _isBookmarkedMap.getOrPut(itemId) {
                MutableStateFlow(isCurrentlyBookmarked)
            }

            Log.d(
                "DuaBookmark",
                "Toggling bookmark for $itemId: currently $isCurrentlyBookmarked"
            )

            if (isCurrentlyBookmarked) {

                bookmarkDao.deleteBookmarkById(bookmarkId, "dua")
                stateFlow.value = false

                Log.d("DuaBookmark", "Removed bookmark for $itemId")

                // 🔽 decrement badge
                onBookmarkCountChanged?.invoke(-1)

            } else {

                val bookmark = BookmarkEntity(
                    id = bookmarkId,
                    itemId = itemId,
                    itemType = "dua",
                    title = title,
                    content = content
                )

                bookmarkDao.insertBookmark(bookmark)
                stateFlow.value = true

                Log.d("DuaBookmark", "Added bookmark for $itemId")

                // 🔼 increment badge
                onBookmarkCountChanged?.invoke(1)
            }
        }
    }

}
