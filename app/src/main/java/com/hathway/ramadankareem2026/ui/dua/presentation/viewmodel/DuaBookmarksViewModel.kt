package com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel

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

class DuaBookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _duaBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val duaBookmarks: StateFlow<List<BookmarkEntity>> = _duaBookmarks.asStateFlow()

    init {
        loadDuaBookmarks()
    }

    private fun loadDuaBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getBookmarksByType("dua").collect { bookmarks ->
                _duaBookmarks.value = bookmarks.sortedByDescending { it.createdAt }
            }
        }
    }
}
