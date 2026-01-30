package com.hathway.ramadankareem2026.ui.bookmarks.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _bookmarks =
        MutableStateFlow<List<com.hathway.ramadankareem2026.data.local.database.BookmarkEntity>>(
            emptyList()
        )
    val bookmarks: StateFlow<List<com.hathway.ramadankareem2026.data.local.database.BookmarkEntity>> =
        _bookmarks.asStateFlow()

    init {
        loadBookmarks()
    }

    private fun loadBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getBookmarksByType("dua").collect { duaBookmarks ->
                _bookmarks.value = duaBookmarks
            }
        }
    }
}
