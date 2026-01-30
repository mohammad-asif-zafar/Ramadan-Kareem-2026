package com.hathway.ramadankareem2026.ui.dua.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DuaBookmarkViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _isBookmarkedMap = mutableMapOf<String, MutableStateFlow<Boolean>>()

    fun isBookmarked(itemId: String): StateFlow<Boolean> {
        return _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }.asStateFlow()
    }

    fun checkBookmarkStatus(itemId: String) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "dua")
            _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }.value =
                isCurrentlyBookmarked
        }
    }

    fun toggleBookmark(itemId: String, title: String, content: String?) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(itemId, "dua")
            val stateFlow = _isBookmarkedMap.getOrPut(itemId) { MutableStateFlow(false) }

            if (isCurrentlyBookmarked) {
                bookmarkDao.deleteBookmarkById(itemId, "dua")
                stateFlow.value = false
            } else {
                val bookmark = com.hathway.ramadankareem2026.data.local.database.BookmarkEntity(
                    id = "dua_${itemId}",
                    itemId = itemId,
                    itemType = "dua",
                    title = title,
                    content = content
                )
                bookmarkDao.insertBookmark(bookmark)
                stateFlow.value = true
            }
        }
    }
}
