package com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel

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

class AllahNameBookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _allahNameBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val allahNameBookmarks: StateFlow<List<BookmarkEntity>> = _allahNameBookmarks.asStateFlow()

    init {
        loadAllahNameBookmarks()
    }

    private fun loadAllahNameBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getBookmarksByType("allah_name").collect { bookmarks ->
                _allahNameBookmarks.value = bookmarks.sortedByDescending { it.createdAt }
            }
        }
    }
}
