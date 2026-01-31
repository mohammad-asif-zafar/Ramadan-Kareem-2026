package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MosqueBookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _mosqueBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val mosqueBookmarks: StateFlow<List<BookmarkEntity>> = _mosqueBookmarks.asStateFlow()

    init {
        loadMosqueBookmarks()
    }

    private fun loadMosqueBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getBookmarksByType("mosque").collect { bookmarks ->
                _mosqueBookmarks.value = bookmarks.sortedByDescending { it.createdAt }
            }
        }
    }
}
